package digital.paynetics.phos.kernel.common.crypto;

public interface EncDec {
    EncryptedItem encrypt(byte[] input);

    byte[] decrypt(EncryptedItem ei);
}
