package digital.paynetics.phos.kernel.common.emv.entry_point.directory_entry;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.emv.tag.TlvUtils;
import java8.util.Optional;


public class FciDirectoryEntryExtractorImpl implements FciDirectoryEntryExtractor {
    private static final int ADF_NAME_MIN_LENGTH = 5;
    private static final int ADF_NAME_MAX_LENGTH = ADF_NAME_MIN_LENGTH + 11;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @Inject
    public FciDirectoryEntryExtractorImpl() {
    }


    @Override
    public List<FciDirectoryEntry> extractCardApplications(byte[] data) throws TlvException {
        List<FciDirectoryEntry> apps = new ArrayList<>();

        List<Tlv> templates;

        templates = TlvUtils.getTlvs(data, EmvTag.APPLICATION_TEMPLATE);
        for (Tlv tlv : templates) {
            Optional<FciDirectoryEntry> fciDirectoryEntryOptional = fromTemplate(tlv);
            if (fciDirectoryEntryOptional.isPresent()) {
                apps.add(fciDirectoryEntryOptional.get());
            }
        }

        return apps;
    }


    private Optional<FciDirectoryEntry> fromTemplate(Tlv tlv) throws TlvException {
        byte[] adfName = TlvUtils.getValue(tlv.getValueBytes(), EmvTag.ADF_NAME);
        byte[] labelRaw = TlvUtils.getValue(tlv.getValueBytes(), EmvTag.APPLICATION_LABEL);

        if (adfName == null) {
            logger.warn("Missing ADF_NAME");
            return Optional.empty();
        }

        if (adfName.length < ADF_NAME_MIN_LENGTH || adfName.length > ADF_NAME_MAX_LENGTH) {
            logger.warn("Invalid ADF_NAME length: {}", adfName.length);
            return Optional.empty();
        }


        String label;
        if (labelRaw != null) {
            label = new String(labelRaw);
        } else {
            label = "";
        }

        byte[] kernelIdentifier = TlvUtils.getValue(tlv.getValueBytes(), EmvTag.KERNEL_IDENTIFIER);
        byte[] priorityRaw = TlvUtils.getValue(tlv.getValueBytes(), EmvTag.APPLICATION_PRIORITY_INDICATOR);
        int priority;
        if (priorityRaw != null) {
            priority = priorityRaw[0] & 0b00001111; // Book1, Table 48: Format of Application Priority Indicator
        } else {
            priority = 0;
        }

        byte[] extendedSelection = TlvUtils.getValue(tlv.getValueBytes(), EmvTag.EXTENDED_SELECTION);


        return Optional.of(new FciDirectoryEntry(adfName, label, priority, kernelIdentifier, extendedSelection));
    }
}
