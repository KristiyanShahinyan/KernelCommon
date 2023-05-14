package digital.paynetics.phos.kernel.common.crypto;


public class EmvPublicKey {
    private final byte[] exponent;
    private final byte[] key;
    private final byte[] remainder;


    public EmvPublicKey(byte[] exponent, byte[] key, byte[] remainder) {
        this.exponent = exponent;
        this.key = key;
        this.remainder = remainder;



    }

    public byte[] getExponent() {
        return exponent;
    }


    public byte[] getKey() {
        return key;
    }


    public byte[] getRemainder() {
        return remainder;
    }


    public byte[] getModulus() {
        byte[] ret;

        if (remainder == null || remainder.length == 0) {
            ret = key.clone();
        } else {
            ret = new byte[key.length + remainder.length];
            System.arraycopy(key, 0, ret, 0, key.length);
            System.arraycopy(remainder, 0, ret, key.length, remainder.length);
        }

        return ret;
    }
}
