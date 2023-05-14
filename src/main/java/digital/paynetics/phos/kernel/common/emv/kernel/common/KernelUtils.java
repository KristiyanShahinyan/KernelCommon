package digital.paynetics.phos.kernel.common.emv.kernel.common;

import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import digital.paynetics.phos.exceptions.EmvException;
import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.TagAndLength;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;

import static digital.paynetics.phos.kernel.common.emv.tag.TlvUtils.encodeTagLength;


public class KernelUtils {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KernelUtils.class);

    /**
     * Extracts {@link Afl} objects from a raw AFL data
     *
     * @param aflRaw AFL byte as returned by GPO
     * @return list of extracted AFLs
     * @throws EmvException if the length of aflRaw is not % 4 == 0
     */
    public static List<Afl> extractAfls(byte[] aflRaw) throws EmvException {
        if (aflRaw.length % 4 != 0 || aflRaw.length > 248) {
            throw new EmvException("Invalid AFL size, length not multiple of 4 or too big");
        }

        List<Afl> list = new ArrayList<>();
        ByteArrayInputStream is = new ByteArrayInputStream(aflRaw);
        int sfi, firstRecord, lastRecord, recordsInvolvedInDataAuthentication;
        while (is.available() >= 4) {
            sfi = is.read() >> 3;
            firstRecord = is.read();
            lastRecord = is.read();
            recordsInvolvedInDataAuthentication = is.read();
            Afl afl = new Afl(sfi, firstRecord, lastRecord, recordsInvolvedInDataAuthentication);
            list.add(afl);
        }

        return list;
    }


    public static byte[] prepareDol(TlvMapReadOnly source, List<TagAndLength> dolList) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (TagAndLength tal : dolList) {

                if (source.isTagPresentAndNonEmpty(tal.getTag())) {
                    Tlv tlv = source.get(tal.getTag());
                    out.write(ByteUtils.fitDolData(tal, tlv.getValueBytes()));
                    logger.debug("Preparing DOL, added: {} {}", tal.getTag().getName(), ByteUtils.toHexString(tlv.getValueBytes()));
                } else {
                    logger.warn("Preparing DOL, missing or empty tag: {} ({})", tal.getTag().getName(),
                            ByteUtils.toHexString(tal.getTag().getTagBytes()));
                    out.write(new byte[tal.getLength()]);
                }
            }

            return out.toByteArray();
        } catch (IOException e) {
            // cannot happen
            throw new RuntimeException(e);
        }
    }


    public static byte[] preparePdol(byte[] pdolData) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(EmvTag.COMMAND_TEMPLATE.getTagBytes());
            out.write(encodeTagLength(pdolData.length));

            out.write(pdolData);
            return out.toByteArray();
        } catch (IOException e) {
            // Cannot happen
            throw new RuntimeException(e);
        }
    }
}
