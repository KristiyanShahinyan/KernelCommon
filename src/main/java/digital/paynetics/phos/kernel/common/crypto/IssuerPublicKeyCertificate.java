package digital.paynetics.phos.kernel.common.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import digital.paynetics.phos.exceptions.CryptoException;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;

import static digital.paynetics.phos.kernel.common.crypto.CryptoUtils.calculateSha1;


public class IssuerPublicKeyCertificate {
    private final EmvPublicKey publicKey;
    private final byte certFormat;
    private final byte[] certExpirationDate;
    private final byte[] certSerialNumber;
    private final int hashAlgorithmIndicator;
    private final String issuerIdentifier;
    private final byte[] hash;
    private final int issuerPublicKeyAlgorithmIndicator;
    private final byte[] issuerIdentifierPaddedBytes;
    private final int issuerPublicKeyModLengthTotal;
    private final int issuerPublicKeyExpLengthTotal;
    private final byte[] issuerPublicKeyPadding;


    public IssuerPublicKeyCertificate(EmvPublicKey publicKey,
                                      byte certFormat,
                                      byte[] certExpirationDate,
                                      byte[] certSerialNumber,
                                      int hashAlgorithmIndicator,
                                      String issuerIdentifier,
                                      byte[] hash,
                                      int issuerPublicKeyAlgorithmIndicator,
                                      byte[] issuerIdentifierPaddedBytes,
                                      int issuerPublicKeyModLengthTotal,
                                      int issuerPublicKeyExpLengthTotal, byte[] issuerPublicKeyPadding) {

        this.publicKey = publicKey;
        this.certFormat = certFormat;
        this.certExpirationDate = certExpirationDate;
        this.certSerialNumber = certSerialNumber;
        this.hashAlgorithmIndicator = hashAlgorithmIndicator;
        this.issuerIdentifier = issuerIdentifier;
        this.hash = hash;
        this.issuerPublicKeyAlgorithmIndicator = issuerPublicKeyAlgorithmIndicator;
        this.issuerIdentifierPaddedBytes = issuerIdentifierPaddedBytes;
        this.issuerPublicKeyModLengthTotal = issuerPublicKeyModLengthTotal;
        this.issuerPublicKeyExpLengthTotal = issuerPublicKeyExpLengthTotal;
        this.issuerPublicKeyPadding = issuerPublicKeyPadding;
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static IssuerPublicKeyCertificate fromBytes(byte[] publicKeyCertificateData,
                                                       byte[] publicKeyRemainder,
                                                       byte[] publicKeyExponent,
                                                       EmvPublicKey parentKey) throws CryptoException {


        byte[] recovered = CryptoUtils.rsaDecrypt(publicKeyCertificateData,
                parentKey.getExponent(),
                parentKey.getModulus());


        ByteArrayInputStream bis = new ByteArrayInputStream(recovered);

        if (bis.read() != 0x6a) { // Header
            throw new CryptoException("Header != 0x6a");
        }

        byte certFormat = (byte) bis.read();

        if (certFormat != 0x02) {
            throw new CryptoException("Invalid certificate format");
        }

        byte[] issuerIdentifierPaddedBytes = new byte[4];

        bis.read(issuerIdentifierPaddedBytes, 0, issuerIdentifierPaddedBytes.length);

        // Remove padding (if any) from issuerIdentifier
        String iiStr = ByteUtils.toHexString(issuerIdentifierPaddedBytes);
        int padStartIndex = iiStr.toUpperCase().indexOf('F');
        if (padStartIndex != -1) {
            iiStr = iiStr.substring(0, padStartIndex);
        }
//        int issuerIdentifier = binaryHexCodedDecimalToInt(iiStr);
        byte[] certExpirationDate = new byte[2];
        byte[] certSerialNumber = new byte[3];

        int hashAlgorithmIndicator;
        int issuerPublicKeyAlgorithmIndicator;

        bis.read(certExpirationDate, 0, certExpirationDate.length);

        bis.read(certSerialNumber, 0, certSerialNumber.length);

        hashAlgorithmIndicator = bis.read() & 0xFF;

        issuerPublicKeyAlgorithmIndicator = bis.read() & 0xFF;

        if (issuerPublicKeyAlgorithmIndicator != 1) {
            throw new CryptoException("Issuer public key algorithm not supported");
        }


        int issuerPublicKeyModLengthTotal = bis.read() & 0xFF;

        int issuerPublicKeyExpLengthTotal = bis.read() & 0xFF;

        int modBytesLength = parentKey.getKey().length - 36;

        int toRead;
        if (publicKeyRemainder != null) {
            toRead = issuerPublicKeyModLengthTotal - publicKeyRemainder.length;
        } else {
            toRead = issuerPublicKeyModLengthTotal;
        }
        byte[] modtmp = new byte[toRead];
        bis.read(modtmp, 0, toRead);
        byte[] padding = null;
        if (issuerPublicKeyModLengthTotal < modBytesLength) {
            padding = new byte[modBytesLength - issuerPublicKeyModLengthTotal];
            bis.read(padding, 0, padding.length);
        }

        EmvPublicKey issuerPublicKey = new EmvPublicKey(publicKeyExponent, modtmp, publicKeyRemainder);

        byte[] hash = new byte[20];
        bis.read(hash, 0, hash.length);

        int trailer = bis.read();

        if (trailer != 0xbc) { // Trailer
            throw new CryptoException("Trailer != 0xbc");
        }

        if (bis.available() > 0) {
            throw new CryptoException("Error parsing certificate. Bytes left=" + bis.available());
        }

        return new IssuerPublicKeyCertificate(issuerPublicKey, certFormat, certExpirationDate, certSerialNumber,
                hashAlgorithmIndicator, iiStr, hash,
                issuerPublicKeyAlgorithmIndicator, issuerIdentifierPaddedBytes,
                issuerPublicKeyModLengthTotal, issuerPublicKeyExpLengthTotal, padding);
    }


    public EmvPublicKey getPublicKey() {
        return publicKey;
    }


    public byte getCertFormat() {
        return certFormat;
    }


    public byte[] getCertExpirationDate() {
        return certExpirationDate;
    }


    public byte[] getCertSerialNumber() {
        return certSerialNumber;
    }


    public int getHashAlgorithmIndicator() {
        return hashAlgorithmIndicator;
    }


    public String getIssuerIdentifier() {
        return issuerIdentifier;
    }


    public byte[] getHash() {
        return hash;
    }


    public int getIssuerPublicKeyAlgorithmIndicator() {
        return issuerPublicKeyAlgorithmIndicator;
    }


    public byte[] getIssuerIdentifierPaddedBytes() {
        return issuerIdentifierPaddedBytes;
    }


    public int getIssuerPublicKeyModLengthTotal() {
        return issuerPublicKeyModLengthTotal;
    }


    public int getIssuerPublicKeyExpLengthTotal() {
        return issuerPublicKeyExpLengthTotal;
    }


    public boolean isHashValid(EmvPublicKey caKey) {
        ByteArrayOutputStream hashStream = new ByteArrayOutputStream();

        hashStream.write(certFormat);
        hashStream.write(issuerIdentifierPaddedBytes, 0, issuerIdentifierPaddedBytes.length);
        hashStream.write(certExpirationDate, 0, certExpirationDate.length);
        hashStream.write(certSerialNumber, 0, certSerialNumber.length);
        hashStream.write((byte) hashAlgorithmIndicator);
        hashStream.write((byte) issuerPublicKeyAlgorithmIndicator);
        hashStream.write((byte) issuerPublicKeyModLengthTotal);
        hashStream.write((byte) issuerPublicKeyExpLengthTotal);

        hashStream.write(publicKey.getKey(), 0, publicKey.getKey().length);
        if (issuerPublicKeyPadding != null) {
            hashStream.write(issuerPublicKeyPadding, 0, issuerPublicKeyPadding.length);
        }
        if (publicKey.getRemainder() != null) {
            hashStream.write(publicKey.getRemainder(), 0, publicKey.getRemainder().length);
        }

        byte[] ipkExponent = publicKey.getExponent();
        hashStream.write(ipkExponent, 0, ipkExponent.length);


        byte[] sha1Result = calculateSha1(hashStream.toByteArray());

        return Arrays.equals(sha1Result, hash);
    }
}
