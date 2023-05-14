package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import digital.paynetics.phos.exceptions.EmvException;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;


/**
 * Reflects CVM List as described in Book 3, section 10.5
 */
public final class CvmList {
    private final long x;
    private final long y;

    private final List<CvmListRule> rules;


    public CvmList(long x, long y, List<CvmListRule> rules) {
        this.x = x;
        this.y = y;
        this.rules = rules;
    }


    public CvmList(byte[] data) throws EmvException {
        if (!isValidDataLength(data)) {
            throw new EmvException("Invalid data length: " + data.length);
        }


        x = ByteUtils.byteArrayToLong(Arrays.copyOfRange(data, 0, 4));
        y = ByteUtils.byteArrayToLong(Arrays.copyOfRange(data, 4, 8));

        rules = new ArrayList<>();

        byte[] tmp = new byte[2];
        for (int i = 8; i < data.length; i = i + 2) {
            System.arraycopy(data, i, tmp, 0, 2);
            CvmListRule rule = new CvmListRule(tmp);
            rules.add(rule);
        }
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidDataLength(byte[] data) {
        return data.length >= 10 && data.length % 2 == 0;
    }


    public long getX() {
        return x;
    }


    public long getY() {
        return y;
    }


    public List<CvmListRule> getRules() {
        return rules;
    }

}
