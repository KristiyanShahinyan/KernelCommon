package digital.paynetics.phos.kernel.common.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CryptoUtils {
    public static byte[] rsaDecrypt(byte[] dataBytes, byte[] expBytes, byte[] modBytes) {

        int inBytesLength = dataBytes.length;

        if (expBytes[0] >= (byte) 0x80) {
            //Prepend 0x00 to modulus
            byte[] tmp = new byte[expBytes.length + 1];
            tmp[0] = (byte) 0x00;
            System.arraycopy(expBytes, 0, tmp, 1, expBytes.length);
            expBytes = tmp;
        }

        if (modBytes[0] >= (byte) 0x80) {
            //Prepend 0x00 to modulus
            byte[] tmp = new byte[modBytes.length + 1];
            tmp[0] = (byte) 0x00;
            System.arraycopy(modBytes, 0, tmp, 1, modBytes.length);
            modBytes = tmp;
        }

        if (dataBytes[0] >= (byte) 0x80) {
            //Prepend 0x00 to signed data to avoid that the most significant bit is interpreted as the "signed" bit
            byte[] tmp = new byte[dataBytes.length + 1];
            tmp[0] = (byte) 0x00;
            System.arraycopy(dataBytes, 0, tmp, 1, dataBytes.length);
            dataBytes = tmp;
        }

        BigInteger exp = new BigInteger(expBytes);
        BigInteger mod = new BigInteger(modBytes);
        BigInteger data = new BigInteger(dataBytes);

        byte[] result = data.modPow(exp, mod).toByteArray();

        if (result.length == (inBytesLength + 1) && result[0] == (byte) 0x00) {
            //Remove 0x00 from beginning of array
            byte[] tmp = new byte[inBytesLength];
            System.arraycopy(result, 1, tmp, 0, inBytesLength);
            result = tmp;
        }

        return result;
    }


    public static byte[] calculateSha1(byte[] data) {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            return sha1.digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static int binaryHexCodedDecimalToInt(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Param hex cannot be null");
        }
        if (hex.length() > 10) {
            throw new IllegalArgumentException("There must be a maximum of 5 hex octets. hex=" + hex);
        }
        try {
            return Integer.parseInt(hex);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Argument hex must be all digits. hex=" + hex, ex);
        }
    }
}
