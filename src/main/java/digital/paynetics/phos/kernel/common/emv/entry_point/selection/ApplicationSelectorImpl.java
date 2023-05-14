package digital.paynetics.phos.kernel.common.emv.entry_point.selection;

import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.inject.Inject;

import digital.paynetics.phos.kernel.common.emv.Outcome;
import digital.paynetics.phos.kernel.common.emv.entry_point.directory_entry.FciDirectoryEntry;
import digital.paynetics.phos.kernel.common.emv.entry_point.directory_entry.FciDirectoryEntryExtractor;
import digital.paynetics.phos.kernel.common.emv.entry_point.misc.IntermediateOutcomeStore;
import digital.paynetics.phos.exceptions.CardDataMissingException;
import digital.paynetics.phos.exceptions.EmvException;
import digital.paynetics.phos.kernel.common.emv.kernel.common.KernelType;
import digital.paynetics.phos.kernel.common.emv.kernel.common.TlvMap;
import digital.paynetics.phos.kernel.common.emv.kernel.common.TlvMapImpl;
import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.TagAndLength;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.emv.tag.TlvUtils;
import digital.paynetics.phos.kernel.common.emv.ui.ContactlessTransactionStatus;
import digital.paynetics.phos.kernel.common.emv.ui.StandardMessages;
import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import digital.paynetics.phos.kernel.common.misc.PreprocessedApplication;
import digital.paynetics.phos.kernel.common.nfc.ApduCommand;
import digital.paynetics.phos.kernel.common.nfc.ApduCommandPackage;
import digital.paynetics.phos.kernel.common.nfc.ApduResponsePackage;
import digital.paynetics.phos.kernel.common.nfc.transceiver.Transceiver;
import java8.util.Optional;


public class ApplicationSelectorImpl implements ApplicationSelector {
    private static final byte[] PPSE = "2PAY.SYS.DDF01".getBytes();

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FciDirectoryEntryExtractor fciDirectoryEntryExtractor;
    private final IntermediateOutcomeStore intermediateOutcomeStore;

    private boolean isInitialized = false;

    private Queue<SelectionCandidate> candidates = new LinkedList<>();

    @Inject
    public ApplicationSelectorImpl(FciDirectoryEntryExtractor fciDirectoryEntryExtractor,
                                   IntermediateOutcomeStore intermediateOutcomeStore) {
        this.fciDirectoryEntryExtractor = fciDirectoryEntryExtractor;
        this.intermediateOutcomeStore = intermediateOutcomeStore;
    }


    @Override
    public Optional<Outcome> init(List<PreprocessedApplication> appsPreprocessed, Transceiver transceiver)
            throws IOException, TlvException, EmvException {


        if (!isInitialized) {

            isInitialized = true;

            // Req 3.3.2.2
            ApduResponsePackage ppseResp = selectPpse(transceiver);

            // Req 3.3.2.3
            if (!ppseResp.isSuccess()) {
                logger.warn("Unsuccessful PPSE selection");

                byte[] rawSw = Arrays.copyOfRange(ppseResp.getData(), ppseResp.getData().length - 2, 2);

                UserInterfaceRequest uiReq = new UserInterfaceRequest(StandardMessages.TRY_ANOTHER_CARD,
                        ContactlessTransactionStatus.NOT_READY, 13, null, null, 0, null);
                Outcome.Builder b = new Outcome.Builder(Outcome.Type.END_APPLICATION);
                b.uiRequestOnOutcome(uiReq);
                b.start(Outcome.Start.B);

                byte[] ddData = new byte[]{0, 0b00000011, 0,
                        rawSw[0],
                        rawSw[1],
                        0x1c
                };
                List<Tlv> ddList = new ArrayList<>();
                ddList.add(new Tlv(EmvTag.ERROR_INDICATION, 6, ddData));
                b.discretionaryData(ddList);

                return Optional.of(b.build());
            }

            if (ppseResp.getData()[0] != 0x6f) {
                logger.warn("No FCI_TEMPLATE FOUND in SELECT PPSE response");

                UserInterfaceRequest uiReq = new UserInterfaceRequest(StandardMessages.TRY_ANOTHER_CARD,
                        ContactlessTransactionStatus.NOT_READY, 13, null, null, 0, null);
                Outcome.Builder b = new Outcome.Builder(Outcome.Type.END_APPLICATION);
                b.uiRequestOnOutcome(uiReq);
                b.start(Outcome.Start.B);

                byte[] ddData = new byte[]{0, 0b00000100, 0,
                        0,
                        0,
                        0x1c
                };
                List<Tlv> ddList = new ArrayList<>();
                ddList.add(new Tlv(EmvTag.ERROR_INDICATION, 6, ddData));
                b.discretionaryData(ddList);

                return Optional.of(b.build());
            }

            // Req 3.3.2.4
            List<FciDirectoryEntry> dirEntries = fciDirectoryEntryExtractor.extractCardApplications(ppseResp.getData());
            if (dirEntries.size() == 0) {
                logger.warn("No apps found on the card.");
                return Optional.of(createEmptyCandidateListOutcome());
            }


            // Workaround contradicting Visa and Mastercard requirements
            // Visa accepts proprietary data objects in PPSE response, Mastercard does not
            FciDirectoryEntry fdeTmp = dirEntries.get(0);
            if (!fdeTmp.getAdfNameAsString().startsWith("A000000003")) { // if not Visa
                checkPpseResponseIsValid(ppseResp.getData());
            }
            ppseResp.purgeData();
            Collections.sort(appsPreprocessed, new Comparator<PreprocessedApplication>() {
                @Override
                public int compare(PreprocessedApplication o1, PreprocessedApplication o2) {
                    return (o1.getAppConfig().getApplicationId().length() - o2.getAppConfig().getApplicationId().length()) * -1;
                }
            });

            List<SelectionCandidate> candidateList = new ArrayList<>();
            // Req 3.3.2.5
            for (PreprocessedApplication pApp : appsPreprocessed) {
                for (FciDirectoryEntry fde : dirEntries) {
                    processCombo(candidateList, pApp, fde);
                }
            }


            Collections.sort(candidateList, new Comparator<SelectionCandidate>() {
                @Override
                public int compare(SelectionCandidate o1, SelectionCandidate o2) {
                    return o1.getPriority() - o2.getPriority();
                }
            });

            candidates.addAll(candidateList);
            logger.debug("Selection candidates: {}", candidateList.size());

            if (candidateList.size() == 0) {
                return Optional.of(createEmptyCandidateListOutcome());
            }
        }

        return Optional.empty();
    }


    private void checkPpseResponseIsValid(byte[] data) throws TlvException, EmvException {
        if (data.length == 0) {
            throw new EmvException("PPSE data is empty");
        }

        if (data[0] != 0x6f) {
            throw new EmvException("PPSE data not File Control Information (FCI) Template");
        }

        List<Tlv> tlvs = TlvUtils.getChildTlvs(data, EmvTag.FCI_TEMPLATE);
        TlvMap map = new TlvMapImpl(tlvs);

        if (!map.isTagPresentAndNonEmpty(EmvTag.DEDICATED_FILE_NAME)) {
            throw new EmvException("Missing DEDICATED_FILE_NAME in PPSE response");
        }


        if (!map.isTagPresentAndNonEmpty(EmvTag.FCI_PROPRIETARY_TEMPLATE)) {
            throw new EmvException("Missing FCI_PROPRIETARY_TEMPLATE in PPSE response");
        }
    }


    @Override
    public Optional<SelectedApplication> select(Transceiver transceiver) throws IOException {
        if (!isInitialized) {
            throw new IllegalStateException("Not initialized. Call init() first");
        }


        // Req 3.3.3.1, 3.3.3.2
        do {
            SelectionCandidate candidate = candidates.poll();
            if (candidate != null) {

                Optional<SelectedApplication> selected = selectApplication(transceiver, candidate);
                if (selected.isPresent()) {
                    return selected;
                }
            } else {
                break;
            }
        } while (true);

        // no selection has succeeded
        return Optional.empty();
    }


    private Optional<SelectedApplication> selectApplication(Transceiver transceiver,
                                                            SelectionCandidate candidate)
            throws IOException {

        byte finalSelection[];
        byte[] extendedSelection = candidate.getExtendedSelection();

        // Req 3.3.3.3
        if (extendedSelection != null && extendedSelection.length > 0 &&
                candidate.getPreprocessedApplication().getAppConfig().isExtendedSelectionSupported()) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(candidate.getAdfName());
            outputStream.write(extendedSelection);
            finalSelection = outputStream.toByteArray();
            logger.debug("Selecting application {} using extended selection",
                    ByteUtils.toHexString(candidate.getAdfName()));
        } else {
            finalSelection = candidate.getAdfName();
            logger.debug("Selecting application {} using simple selection",
                    ByteUtils.toHexString(candidate.getAdfName()));
        }

        // Req 3.3.3.4
        ApduCommandPackage cmd = new ApduCommandPackage(ApduCommand.SELECT, finalSelection);
        ApduResponsePackage resp = transceiver.transceive(cmd);

        // Req 3.3.3.5
        if (resp.isSuccess()) {
            // Req 3.4.1.2 - Entry Point shall make the Entry Point Pre-Processing Indicators available to kernel
            // Req 3.4.1.3 - SelectedApplication object represents FCI template
            SelectedApplication sa;
            try {
                sa = SelectedApplication.createFromRawData(candidate, resp.getData());
                resp.purgeData();
            } catch (TlvException e) {
                resp.purgeData();
                logger.warn(e.getMessage());

                if (candidate.getPreprocessedApplication().getAppConfig().getApplicationId().startsWith("A000000004")) {
                    Outcome.Builder b = new Outcome.Builder(Outcome.Type.SELECT_NEXT);
                    b.start(Outcome.Start.C);
                    List<Tlv> dd = new ArrayList<>();
                    dd.add(new Tlv(EmvTag.ERROR_INDICATION, 6, new byte[]{0, 0b00000100, 0, 0, 0, 0}));
                    b.discretionaryData(dd);
                    intermediateOutcomeStore.add(b.build());
                }

                return Optional.empty();
            } catch (CardDataMissingException e) {
                resp.purgeData();
                logger.warn(e.getMessage());
                if (candidate.getPreprocessedApplication().getAppConfig().getApplicationId().startsWith("A000000004")) {
                    Outcome.Builder b = new Outcome.Builder(Outcome.Type.SELECT_NEXT);
                    b.start(Outcome.Start.C);
                    List<Tlv> dd = new ArrayList<>();
                    dd.add(new Tlv(EmvTag.ERROR_INDICATION, 6, new byte[]{0, 0b00000001, 0, 0, 0, 0}));
                    b.discretionaryData(dd);
                    intermediateOutcomeStore.add(b.build());
                }

                return Optional.empty();
            }

            // Req 3.3.3.6
            if (candidate.getFinalKernelType() == KernelType.VISA) {
                List<TagAndLength> pdol = sa.getPdol();
                if (pdol.size() > 0) {
                    boolean ttqTagPresent = false;

                    for (TagAndLength tal : pdol) {
                        if (tal.getTag() == EmvTag.TERMINAL_TRANSACTION_QUALIFIERS__PUNATC_TRACK2) {
                            ttqTagPresent = true;
                            break;
                        }
                    }
                    if (ttqTagPresent) {
                        return Optional.of(sa);
                    } else {
                        // Kernel 1 not supported so we just skip this selection
                        return Optional.empty();
                    }
                } else {
                    // Kernel 1 not supported so we just skip this selection
                    return Optional.empty();
                }
            } else {
                return Optional.of(sa);
            }
        } else {
            return Optional.empty();
        }
    }


    private void processCombo(List<SelectionCandidate> candidates, PreprocessedApplication pApp, FciDirectoryEntry fde) {
        // Req 3.3.2.5 a
        if (fde.isAdfNamePresent()) {
            // Req 3.3.2.5 b
            if (fde.getAdfNameAsString().equals(pApp.getAppConfig().getApplicationId()) ||
                    fde.getAdfNameAsString().startsWith(pApp.getAppConfig().getApplicationId())) {

                KernelType finalKernelType;
                // Req 3.3.2.5 c
                // Visa does not recognize 9F2A tag, so we must put exception for VISA here...
                if (fde.isKernelIdentifierPresent() && pApp.getKernelType() != KernelType.VISA) {
                    BigInteger tmp = BigInteger.valueOf(fde.getKernelIdentifier()[0]);
                    if (!tmp.testBit(8 - 1)) { // we support only EMV kernels (i.e. no domestic kernel)
                        int requestedKernelId = (int) fde.getKernelIdentifier()[0];
                        if (requestedKernelId == 0) {
                            finalKernelType = pApp.getKernelType();
                            candidates.add(new SelectionCandidate(fde.getAdfName(),
                                    pApp,
                                    finalKernelType,
                                    fde.getPriority(),
                                    fde.getExtendedSelection()
                            ));
                        } else if (requestedKernelId == pApp.getKernelType().getEmvKernelIdentifier()) {
                            finalKernelType = pApp.getKernelType();
                            candidates.add(new SelectionCandidate(fde.getAdfName(),
                                    pApp,
                                    finalKernelType,
                                    fde.getPriority(),
                                    fde.getExtendedSelection()
                            ));
                        } else if (pApp.getKernelType() == KernelType.MASTERCARD) { // exception for MCL S52.12
                            //noinspection ConstantConditions
                            if (requestedKernelId == 0b01000000 || requestedKernelId == 0b01000010 ||
                                    requestedKernelId == 0b00000010) {

                                finalKernelType = pApp.getKernelType();
                                candidates.add(new SelectionCandidate(fde.getAdfName(),
                                        pApp,
                                        finalKernelType,
                                        fde.getPriority(),
                                        fde.getExtendedSelection()
                                ));

                            }
                        } else {
                            logger.debug("Requested kernel {} not supported for the combo", requestedKernelId);
                        }
                    }
                } else {
                    finalKernelType = pApp.getKernelType();
                    boolean alreadyAdded = false;
                    for (SelectionCandidate s : candidates) {
                        if (Arrays.equals(s.getAdfName(), fde.getAdfName())) {
                            alreadyAdded = true;
                            break;
                        }
                    }

                    if (fde.getAdfNameAsString().startsWith("A000000003")) {
                        // we passed all Visa tests with this, so it stays
                        if (!alreadyAdded) {
                            candidates.add(new SelectionCandidate(fde.getAdfName(),
                                    pApp,
                                    finalKernelType,
                                    fde.getPriority(),
                                    fde.getExtendedSelection()
                            ));
                        }
                    } else {
                        candidates.add(new SelectionCandidate(fde.getAdfName(),
                                pApp,
                                finalKernelType,
                                fde.getPriority(),
                                fde.getExtendedSelection()
                        ));
                    }
                }
            }
        }
    }


    private ApduResponsePackage selectPpse(Transceiver transceiver) throws IOException {
        logger.debug("About to execute SELECT PPSE");
        ApduCommandPackage pkg = new ApduCommandPackage(ApduCommand.SELECT, PPSE);
        return transceiver.transceive(pkg);
    }


    private Outcome createEmptyCandidateListOutcome() {
        UserInterfaceRequest uiReq = new UserInterfaceRequest(StandardMessages.TRY_ANOTHER_CARD,
                ContactlessTransactionStatus.NOT_READY, 13, null, null, 0, null);
        Outcome.Builder b = new Outcome.Builder(Outcome.Type.END_APPLICATION);
        b.uiRequestOnOutcome(uiReq);
        b.start(Outcome.Start.B);

        byte[] ddData = new byte[]{0, 0b00001010, 0,
                0,
                0,
                0x1c
        };
        List<Tlv> ddList = new ArrayList<>();
        ddList.add(new Tlv(EmvTag.ERROR_INDICATION, 6, ddData));
        b.discretionaryData(ddList);

        return b.build();

    }
}
