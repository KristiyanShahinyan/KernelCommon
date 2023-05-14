package digital.paynetics.phos.kernel.common.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import digital.paynetics.phos.exceptions.InvalidDynamicApplicationData;

import static digital.paynetics.phos.kernel.common.crypto.CryptoUtils.calculateSha1;
import static digital.paynetics.phos.kernel.common.crypto.CryptoUtils.rsaDecrypt;


public class SignedDynamicApplicationData {
    private final byte header;
    private final byte signedDataFormat;
    private final byte hashAlgorithmIndicator;

    private final byte[] iccDynamicNumber;
    private final byte[] hashResult;
    private final byte cryptogramInformationData;
    private final byte[] applicationCryptogram;
    private final byte[] transactionDataHash;

    private final byte[] iccDynamicData;
    private final byte[] padding;

    private final byte[] dsSummary2;
    private final byte[] dsSummary3;
    private final byte[] terminalRelayResistanceEntropy;
    private final byte[] deviceRelayResistanceEntropy;
    private final byte[] minTimeForProcessingRelayResistanceApdu;
    private final byte[] maxTimeForProcessingRelayResistanceApdu;
    private final byte[] deviceEstimatedTransmissionTimeForRelayResistanceRapdu;




    public SignedDynamicApplicationData(byte header,
                                        byte signedDataFormat,
                                        byte hashAlgorithmIndicator,
                                        byte[] iccDynamicNumber,
                                        byte[] hashResult,
                                        byte cryptogramInformationData,
                                        byte[] applicationCryptogram,
                                        byte[] transactionDataHash,
                                        byte[] iccDynamicData,
                                        byte[] padding,
                                        byte[] dsSummary2,
                                        byte[] dsSummary3,
                                        byte[] terminalRelayResistanceEntropy,
                                        byte[] deviceRelayResistanceEntropy,
                                        byte[] minTimeForProcessingRelayResistanceApdu,
                                        byte[] maxTimeForProcessingRelayResistanceApdu,
                                        byte[] deviceEstimatedTransmissionTimeForRelayResistanceRapdu) {

        this.header = header;
        this.signedDataFormat = signedDataFormat;
        this.hashAlgorithmIndicator = hashAlgorithmIndicator;
        this.cryptogramInformationData = cryptogramInformationData;
        this.applicationCryptogram = applicationCryptogram;
        this.transactionDataHash = transactionDataHash;
        this.iccDynamicNumber = iccDynamicNumber;
        this.hashResult = hashResult;
        this.iccDynamicData = iccDynamicData;
        this.padding = padding;
        this.dsSummary2 = dsSummary2;
        this.dsSummary3 = dsSummary3;
        this.terminalRelayResistanceEntropy = terminalRelayResistanceEntropy;
        this.deviceRelayResistanceEntropy = deviceRelayResistanceEntropy;
        this.minTimeForProcessingRelayResistanceApdu = minTimeForProcessingRelayResistanceApdu;
        this.maxTimeForProcessingRelayResistanceApdu = maxTimeForProcessingRelayResistanceApdu;
        this.deviceEstimatedTransmissionTimeForRelayResistanceRapdu = deviceEstimatedTransmissionTimeForRelayResistanceRapdu;
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static SignedDynamicApplicationData fromBytes(byte[] encryptedSdad,
                                                         EmvPublicKey cardPublicKey,
                                                         boolean haveIds,
                                                         boolean idsVersion2,
                                                         boolean haveRrp
    ) throws InvalidDynamicApplicationData {

        // Book 2, 6.6.2 Dynamic Signature Verification

        // 1
        if (encryptedSdad.length != cardPublicKey.getModulus().length) {
            throw new InvalidDynamicApplicationData("encryptedSdad.length != modulus length");
        }

        byte[] decrypted = rsaDecrypt(encryptedSdad, cardPublicKey.getExponent(), cardPublicKey.getModulus());

        ByteArrayInputStream stream = new ByteArrayInputStream(decrypted);

        byte header = (byte) stream.read();

        // 3
        if (header != (byte) 0x6a) {
            throw new InvalidDynamicApplicationData("Header != 0x6a");
        }

        byte signedDataFormat = (byte) stream.read();

        // 4
        if (signedDataFormat != (byte) 0x05) {
            throw new InvalidDynamicApplicationData("Signed Data Format != 0x05");
        }

        byte hashAlgorithmIndicator = (byte) stream.read(); // We currently support only SHA-1
        int iccDynamicDataLength = ((byte) stream.read()) & 0xFF;

        byte[] iccDynamicData = new byte[iccDynamicDataLength];

        int actualDdLength = stream.read(iccDynamicData, 0, iccDynamicDataLength);
        if (actualDdLength != iccDynamicDataLength) {
            throw new InvalidDynamicApplicationData("actualDdLength != iccDynamicDataLength");
        }

        if (actualDdLength < 30) { // in all paths length is required to be 30 or greater
            throw new InvalidDynamicApplicationData("iccDynamicData length < 30");
        }

        ByteArrayInputStream iccDynamicDataIs = new ByteArrayInputStream(iccDynamicData);

        int iccDynamicNumberLength = ((byte) iccDynamicDataIs.read()) & 0xFF;
        byte[] iccDynamicNumber = new byte[iccDynamicNumberLength];


        readDdi(iccDynamicDataIs, iccDynamicNumber, 0, iccDynamicNumberLength);
        byte cid = (byte) iccDynamicDataIs.read();
        byte[] applicationCryptogram = new byte[8];
        readDdi(iccDynamicDataIs, applicationCryptogram, 0, 8);
        byte[] transactionDataHash = new byte[20];
        readDdi(iccDynamicDataIs, transactionDataHash, 0, 20);

        byte[] dsSummary2 = null;
        byte[] dsSummary3 = null;
        byte[] terminalRelayResistanceEntropy = null;
        byte[] deviceRelayResistanceEntropy = null;
        byte[] minTimeForProcessingRelayResistanceApdu = null;
        byte[] maxTimeForProcessingRelayResistanceApdu = null;
        byte[] deviceEstimatedTransmissionTimeForRelayResistanceRapdu = null;
        if (haveIds) {
            if (idsVersion2) {
                dsSummary2 = new byte[16];
                dsSummary3 = new byte[16];
                int ds2len = iccDynamicDataIs.read(dsSummary2, 0, 16);
                if (ds2len < 16) {
                    dsSummary2 = null;
                    dsSummary3 = null;
                } else {
                    int ds3len = iccDynamicDataIs.read(dsSummary3, 0, 16);
                    if (ds3len < 16) {
                        dsSummary3 = null;
                    }
                }
            } else {
                dsSummary2 = new byte[8];
                dsSummary3 = new byte[8];
                int ds2len = iccDynamicDataIs.read(dsSummary2, 0, 8);
                if (ds2len < 8) {
                    dsSummary2 = null;
                    dsSummary3 = null;
                } else {
                    int ds3len = iccDynamicDataIs.read(dsSummary3, 0, 8);
                    if (ds3len < 8) {
                        dsSummary3 = null;
                    }
                }
            }
        }

        if (haveRrp) {
            terminalRelayResistanceEntropy = new byte[4];
            deviceRelayResistanceEntropy = new byte[4];
            minTimeForProcessingRelayResistanceApdu = new byte[2];
            maxTimeForProcessingRelayResistanceApdu = new byte[2];
            deviceEstimatedTransmissionTimeForRelayResistanceRapdu = new byte[2];

            readDdi(iccDynamicDataIs, terminalRelayResistanceEntropy, 0, 4);
            readDdi(iccDynamicDataIs, deviceRelayResistanceEntropy, 0, 4);
            readDdi(iccDynamicDataIs, minTimeForProcessingRelayResistanceApdu, 0, 2);
            readDdi(iccDynamicDataIs, maxTimeForProcessingRelayResistanceApdu, 0, 2);
            readDdi(iccDynamicDataIs, deviceEstimatedTransmissionTimeForRelayResistanceRapdu, 0, 2);
        }

        //Now read padding bytes (0xbb), if available
        //The padding bytes are used in hash validation
        byte[] padding = new byte[stream.available() - 21];
        stream.read(padding, 0, padding.length);

        byte[] hashResult = new byte[20];

        stream.read(hashResult, 0, 20);

        byte trailer = (byte) stream.read();

        // 2
        if (trailer != (byte) 0xbc) {
            throw new InvalidDynamicApplicationData("Trailer != 0xbc");
        }

        return new SignedDynamicApplicationData(header, signedDataFormat, hashAlgorithmIndicator, iccDynamicNumber,
                hashResult, cid, applicationCryptogram, transactionDataHash, iccDynamicData, padding,
                dsSummary2, dsSummary3, terminalRelayResistanceEntropy, deviceRelayResistanceEntropy,
                minTimeForProcessingRelayResistanceApdu, maxTimeForProcessingRelayResistanceApdu,
                deviceEstimatedTransmissionTimeForRelayResistanceRapdu);
    }


    private static void readDdi(ByteArrayInputStream iccDynamicDataIs, byte b[], int off, int len)
            throws InvalidDynamicApplicationData {

        int bytesRead = iccDynamicDataIs.read(b, off, len);
        if (bytesRead <= 0) {
            throw new InvalidDynamicApplicationData("Not enough data");
        }
    }


    public byte getHeader() {
        return header;
    }


    public byte getSignedDataFormat() {
        return signedDataFormat;
    }


    public byte getHashAlgorithmIndicator() {
        return hashAlgorithmIndicator;
    }


    public byte[] getIccDynamicNumber() {
        return iccDynamicNumber;
    }


    public byte[] getHashResult() {
        return hashResult;
    }


//    public boolean isValidHash(byte[] terminalDynamicData) {
//        ByteArrayOutputStream hashStream = new ByteArrayOutputStream();
//
//        //EMV Book 2, page 67, table 15
//
//        //Header not included in hash
//        hashStream.write(signedDataFormat);
//        hashStream.write(hashAlgorithmIndicator);
//        hashStream.write(iccDynamicData.length);
//        hashStream.write(iccDynamicData, 0, iccDynamicData.length);
//        hashStream.write(padding, 0, padding.length);
//        hashStream.write(terminalDynamicData, 0, terminalDynamicData.length);
//        //Trailer not included in hash
//
//        byte[] sha1Result = null;
//        sha1Result = calculateSha1(hashStream.toByteArray());
//
//        return Arrays.equals(sha1Result, hashResult);
//    }


    public byte getCryptogramInformationData() {
        return cryptogramInformationData;
    }


    public byte[] getApplicationCryptogram() {
        return applicationCryptogram;
    }


    public boolean checkHashValid(byte[] terminalRandomNumber) {
        ByteArrayOutputStream bis = new ByteArrayOutputStream();

        bis.write(signedDataFormat);
        bis.write(hashAlgorithmIndicator);
        bis.write((byte) iccDynamicData.length);
        bis.write(iccDynamicData, 0, iccDynamicData.length);
        bis.write(padding, 0, padding.length);
        bis.write(terminalRandomNumber, 0, terminalRandomNumber.length);

        byte[] actualHash = calculateSha1(bis.toByteArray());

        return Arrays.equals(actualHash, hashResult);
    }


    public byte[] getTransactionDataHash() {
        return transactionDataHash;
    }


    public byte[] getDsSummary2() {
        return dsSummary2;
    }


    public byte[] getDsSummary3() {
        return dsSummary3;
    }


    public byte[] getTerminalRelayResistanceEntropy() {
        return terminalRelayResistanceEntropy;
    }


    public byte[] getDeviceRelayResistanceEntropy() {
        return deviceRelayResistanceEntropy;
    }


    public byte[] getMinTimeForProcessingRelayResistanceApdu() {
        return minTimeForProcessingRelayResistanceApdu;
    }


    public byte[] getMaxTimeForProcessingRelayResistanceApdu() {
        return maxTimeForProcessingRelayResistanceApdu;
    }


    public byte[] getDeviceEstimatedTransmissionTimeForRelayResistanceRapdu() {
        return deviceEstimatedTransmissionTimeForRelayResistanceRapdu;
    }
}
