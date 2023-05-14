package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.math.BigInteger;

import digital.paynetics.phos.exceptions.EmvException;


/**
 * Reflects Application Interchange Profile as described in Book 3, Annex C.1 and Book C-2, A.1.16
 */
public final class ApplicationInterchangeProfile {
    private final boolean sdaSupported;
    private final boolean ddaSupported;
    private final boolean cvmSupported;
    private final boolean terminalRiskManagementRequest;
    private final boolean issuerAuthenticationSupport;
    private final boolean onDeviceCvmSupported;
    private final boolean cdaSupported;
    private final boolean emvModeSupported;
    private final boolean relayResistanceSupported;


    public ApplicationInterchangeProfile(boolean sdaSupported, boolean ddaSupported, boolean cvmSupported,
                                         boolean terminalRiskManagementRequest, boolean issuerAuthenticationSupport,
                                         boolean onDeviceCvmSupported, boolean cdaSupported, boolean emvModeSupported,
                                         boolean relayResistanceSupported) {

        this.sdaSupported = sdaSupported;
        this.ddaSupported = ddaSupported;
        this.cvmSupported = cvmSupported;
        this.terminalRiskManagementRequest = terminalRiskManagementRequest;
        this.issuerAuthenticationSupport = issuerAuthenticationSupport;
        this.onDeviceCvmSupported = onDeviceCvmSupported;
        this.cdaSupported = cdaSupported;
        this.emvModeSupported = emvModeSupported;
        this.relayResistanceSupported = relayResistanceSupported;
    }


    public ApplicationInterchangeProfile(byte[] bytes) throws EmvException {
        if (bytes.length != 2) {
            throw new EmvException("Invalid bytes length");
        }

        BigInteger b1 = BigInteger.valueOf(bytes[0]);
        sdaSupported = b1.testBit(7 - 1);
        ddaSupported = b1.testBit(6 - 1);
        cvmSupported = b1.testBit(5 - 1);
        terminalRiskManagementRequest = b1.testBit(4 - 1);
        issuerAuthenticationSupport = b1.testBit(3 - 1);
        onDeviceCvmSupported = b1.testBit(2 - 1);

        //noinspection PointlessArithmeticExpression
        cdaSupported = b1.testBit(1 - 1);


        BigInteger b2 = BigInteger.valueOf(bytes[1]);
        emvModeSupported = b2.testBit(8 - 1);

        //noinspection PointlessArithmeticExpression
        relayResistanceSupported = b2.testBit(1 - 1);
    }


    public boolean isSdaSupported() {
        return sdaSupported;
    }


    public boolean isDdaSupported() {
        return ddaSupported;
    }


    public boolean isCvmSupported() {
        return cvmSupported;
    }


    public boolean isTerminalRiskManagementRequest() {
        return terminalRiskManagementRequest;
    }


    public boolean isIssuerAuthenticationSupport() {
        return issuerAuthenticationSupport;
    }


    public boolean isOnDeviceCvmSupported() {
        return onDeviceCvmSupported;
    }


    public boolean isCdaSupported() {
        return cdaSupported;
    }


    public boolean isEmvModeSupported() {
        return emvModeSupported;
    }


    public boolean isRelayResistanceSupported() {
        return relayResistanceSupported;
    }
}
