package digital.paynetics.phos.kernel.common.nfc;


import org.slf4j.LoggerFactory;

import java.util.Arrays;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;


public class ApduResponsePackage {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ApduResponsePackage.class);


    private final byte[] data;
    private final ApduResponseStatusWord statusWord;
    private volatile boolean isPurged = false;


    public ApduResponsePackage(final byte[] data) throws Exception {
        if (data.length < 2) {
            throw new Exception("data length is < 2");
        }
        this.data = data;
        this.statusWord = ApduResponseStatusWord.fromData(data);
    }


    public boolean isSuccess() {
        return statusWord == ApduResponseStatusWord.SW_9000;
    }


    public byte[] getData() {
        return data.clone();
    }


    public byte[] getDataNoStatusBytes() {
        return Arrays.copyOfRange(data, 0, data.length - 2);
    }


    public ApduResponseStatusWord getStatusWord() {
        return statusWord;
    }


    public void purgeData() {
        Arrays.fill(data, (byte) 0);
        isPurged = true;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (!isPurged) {
            logger.warn("ApduResponsePackage not purged!!! Data: {}", ByteUtils.toHexString(data));
        }
    }
}
