package digital.paynetics.phos.kernel.common.crypto;

public final class EncryptedItem {
    private final byte[] data;
    private final byte[] iv;


    public EncryptedItem(byte[] data, byte[] iv) {
        this.data = data;
        this.iv = iv;
    }


    public byte[] getData() {
        return data.clone();
    }


    public byte[] getIv() {
        return iv.clone();
    }
}
