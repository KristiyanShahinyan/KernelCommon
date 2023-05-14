package digital.paynetics.phos.kernel.common.emv.entry_point.selection;

import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import digital.paynetics.phos.kernel.common.emv.entry_point.misc.IssuerCodeTableIndex;
import digital.paynetics.phos.exceptions.CardDataMissingException;
import digital.paynetics.phos.kernel.common.emv.kernel.common.TlvMap;
import digital.paynetics.phos.kernel.common.emv.kernel.common.TlvMapImpl;
import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.TagAndLength;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.emv.tag.TlvUtils;
import java8.util.Optional;


public final class SelectedApplication {
    private static final String DEFAULT_ENCODING = "ISO-8859-1";
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SelectedApplication.class);

    private final Optional<String> label;
    private final int priorityIndicator;
    private final Optional<String> languagePreference;
    private final List<TagAndLength> pdol;
    private final Optional<String> applicationPreferredName;
    private final SelectionCandidate candidate;
    private final byte[] dfName;
    private final List<Tlv> appTlvs;
    private final byte[] rawData;


    SelectedApplication(
            SelectionCandidate candidate,
            String label,
            int priorityIndicator,
            String languagePreference,
            List<TagAndLength> pdol,
            String applicationPreferredName,
            byte[] dfName,
            List<Tlv> appTlvs,
            byte[] rawData) {

        this.label = Optional.ofNullable(label);
        this.priorityIndicator = priorityIndicator;
        this.languagePreference = Optional.ofNullable(languagePreference);
        this.pdol = pdol;
        this.applicationPreferredName = Optional.ofNullable(applicationPreferredName);
        this.candidate = candidate;
        this.dfName = dfName;
        this.appTlvs = appTlvs;
        this.rawData = rawData;
    }


    public static SelectedApplication createFromRawData(
            SelectionCandidate candidate,
            byte[] data
    ) throws TlvException, CardDataMissingException {

        Builder b = new Builder(candidate);
        b.rawData(data);

        List<Tlv> tlvsFci = TlvUtils.getChildTlvs(data, EmvTag.FCI_TEMPLATE);
        if (tlvsFci.size() == 0) {
            throw new CardDataMissingException("Missing FCI_TEMPLATE");
        }

        TlvMap mapFci = new TlvMapImpl();
        mapFci.addAll(tlvsFci);


        if (!candidate.getPreprocessedApplication().getAppConfig().getApplicationId().startsWith("A000000004")) { // not Mastercard
            if (!mapFci.isTagPresentAndNonEmpty(EmvTag.DEDICATED_FILE_NAME) ||
                    !mapFci.isTagPresentAndNonEmpty(EmvTag.FCI_PROPRIETARY_TEMPLATE)) {
                throw new CardDataMissingException("Missing DEDICATED_FILE_NAME or FCI_PROPRIETARY_TEMPLATE");
            }
        } else {
            // exception for Mastercard because they always do it their own way. ffs...
//            if (!mapFci.isTagPresentAndNonEmpty(EmvTag.DEDICATED_FILE_NAME) ||
//                    !mapFci.isTagPresent(EmvTag.FCI_PROPRIETARY_TEMPLATE)) {
//                throw new CardDataMissingException("Missing DEDICATED_FILE_NAME or FCI_PROPRIETARY_TEMPLATE");
//            }

            return b.build();
        }

//        String dfName = mapFci.get(EmvTag.DEDICATED_FILE_NAME).getValueAsHex();
//        // Exception for Mastercard - it mandates that TLVs have to be in their templates or it is a parsing error
//        if (dfName.startsWith("A000000004")) {
//            for (Tlv tlv : tlvsFci) {
//                if (tlv.getTag() != EmvTag.DEDICATED_FILE_NAME && tlv.getTag() != EmvTag.FCI_PROPRIETARY_TEMPLATE) {
//                    if (MastercardTags.isKnown(tlv.getTag())) {
//                        throw new ParsingException("Unexpected TLV(s) in FCI template");
//                    }
//                }
//            }
//        }


        b.dfName(mapFci.get(EmvTag.DEDICATED_FILE_NAME).getValueBytes());

        if (data.length > 0) {
            List<Tlv> tlvs = TlvUtils.getTlvs(mapFci.get(EmvTag.FCI_PROPRIETARY_TEMPLATE).getValueBytes());

            TlvMap map = new TlvMapImpl();
            map.addAll(tlvs);

            if (map.isTagPresentAndNonEmpty(EmvTag.APPLICATION_LABEL)) {
                b.label(map.get(EmvTag.APPLICATION_LABEL).getValueAsString());
            }
            // According to EMV 4.3 Book 1, Table 45 Application label is mandatory but Mastercard and Visa think otherwise
//            else {
//                throw new EmvException("Missing APPLICATION_LABEL. Violates EMV Book 1, Table 45");
//            }

            if (map.isTagPresentAndNonEmpty(EmvTag.APPLICATION_PRIORITY_INDICATOR)) {
                b.priorityIndicator(map.get(EmvTag.APPLICATION_PRIORITY_INDICATOR).getValueAsHexInt());
            }

            if (map.isTagPresentAndNonEmpty(EmvTag.LANGUAGE_PREFERENCE)) {
                b.languagePreference(map.get(EmvTag.LANGUAGE_PREFERENCE).getValueAsString());
            }

            if (map.isTagPresentAndNonEmpty(EmvTag.PDOL)) {
                b.pdol(map.get(EmvTag.PDOL).getValueBytes());
            }

            IssuerCodeTableIndex encodingEnum = null;
            if (map.isTagPresentAndNonEmpty(EmvTag.ISSUER_CODE_TABLE_INDEX)) {
                int encoding = map.get(EmvTag.ISSUER_CODE_TABLE_INDEX).getValueAsHexInt();
                encodingEnum = IssuerCodeTableIndex.valueOf(encoding);
                if (encodingEnum == null) {
                    logger.warn("Unable to resolve IssuerCodeTableIndex with value: " + encoding);
                }
            }


            if (map.isTagPresentAndNonEmpty(EmvTag.APP_PREFERRED_NAME)) {
                if (encodingEnum != null) {
                    try {
                        String appPreferredName = new String(map.get(EmvTag.APP_PREFERRED_NAME).getValueBytes(), encodingEnum.getCodeTableName());
                        b.applicationPreferredName(appPreferredName);
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Error: {}", e);
                    }
                } else {
                    try {
                        String appPreferredName = new String(map.get(EmvTag.APP_PREFERRED_NAME).getValueBytes(), DEFAULT_ENCODING);
                        b.applicationPreferredName(appPreferredName);
                    } catch (UnsupportedEncodingException e) {
                        logger.error("Error: {}", e);
                    }
                }

            }

            List<Tlv> appTlvs = new ArrayList<>();
            for (Tlv tlv : tlvs) {
                if (tlv.getTag() == EmvTag.FCI_ISSUER_DISCRETIONARY_DATA) {
                    appTlvs.addAll(TlvUtils.getTlvs(tlv.getValueBytes()));
                } else {
                    appTlvs.add(tlv);
                }
            }
            b.appTlvs(appTlvs);
        }


        return b.build();
    }


    public Optional<String> getLabel() {
        return label;
    }


    public int getPriorityIndicator() {
        return priorityIndicator;
    }


    public Optional<String> getLanguagePreference() {
        return languagePreference;
    }


    public List<TagAndLength> getPdol() {
        return pdol;
    }


    public Optional<String> getApplicationPreferredName() {
        return applicationPreferredName;
    }


    public SelectionCandidate getCandidate() {
        return candidate;
    }


    public byte[] getDfName() {
        return dfName;
    }


    public List<Tlv> getAppTlvs() {
        return appTlvs;
    }


    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {
        private final SelectionCandidate candidate;
        private String label = null;
        private int priorityIndicator = -1;
        private String languagePreference = null;
        private List<TagAndLength> pdol = new ArrayList<>();
        private String applicationPreferredName = null;
        private byte[] dfName = null;
        private List<Tlv> appTlvs = new ArrayList<>();
        private byte[] rawData;


        public Builder(SelectionCandidate candidate) {
            this.candidate = candidate;
        }


        public Builder label(String label) {
            this.label = label;
            return this;
        }


        public Builder priorityIndicator(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid indicator: " + i);
            }
            this.priorityIndicator = i;
            return this;
        }


        public Builder languagePreference(String p) {
            languagePreference = p;
            return this;
        }


        public Builder pdol(byte[] pdol) throws TlvException {
            if (pdol.length == 0) {
                throw new IllegalArgumentException("pdol.length == 0");
            }

            this.pdol = TlvUtils.parseTagAndLength(pdol);

            return this;
        }


        public Builder applicationPreferredName(String applicationPreferredName) {
            this.applicationPreferredName = applicationPreferredName;

            return this;
        }


        public Builder dfName(byte[] dfName) {
            this.dfName = dfName;

            return this;
        }


        public Builder appTlvs(List<Tlv> appTlvs) {
            this.appTlvs.addAll(appTlvs);

            return this;
        }


        public Builder rawData(byte[] rawData) {
            this.rawData = rawData;

            return this;
        }


        public SelectedApplication build() {
            return new SelectedApplication(candidate, label, priorityIndicator, languagePreference, pdol,
                    applicationPreferredName, dfName, appTlvs, rawData);
        }
    }


    public byte[] getRawData() {
        return rawData;
    }
}
