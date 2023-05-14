package digital.paynetics.phos.kernel.common.emv;


public class TtqFinal {
    public final boolean isMagStripeSupported;
    public final boolean isEmvModeSupported;
    public final boolean isEmvContactChipSupported;
    public final boolean isOfflineOnlyReader;
    public final boolean isOnlinePinSupported;
    public final boolean isSignatureSupported;
    public final boolean isOfflineDataAuthenticationForOnlineAuthorizationsSupported;
    public final boolean isOnlineCryptogramRequired;
    public final boolean isCvmRequired;
    public final boolean isOfflinePinSupported;
    public final boolean isIssuerUpdateProcessingSupported;
    public final boolean isConsumerDeviceCvmSupported;


    public TtqFinal(boolean isMagStripeSupported, boolean isEmvModeSupported, boolean isEmvContactChipSupported,
                    boolean isOfflineOnlyReader, boolean isOnlinePinSupported, boolean isSignatureSupported,
                    boolean isOfflineDataAuthenticationForOnlineAuthorizationsSupported,
                    boolean isOnlineCryptogramRequired, boolean isCvmRequired, boolean isOfflinePinSupported,
                    boolean isIssuerUpdateProcessingSupported, boolean isConsumerDeviceCvmSupported) {

        this.isMagStripeSupported = isMagStripeSupported;
        this.isEmvModeSupported = isEmvModeSupported;
        this.isEmvContactChipSupported = isEmvContactChipSupported;
        this.isOfflineOnlyReader = isOfflineOnlyReader;
        this.isOnlinePinSupported = isOnlinePinSupported;
        this.isSignatureSupported = isSignatureSupported;
        this.isOfflineDataAuthenticationForOnlineAuthorizationsSupported = isOfflineDataAuthenticationForOnlineAuthorizationsSupported;
        this.isOnlineCryptogramRequired = isOnlineCryptogramRequired;
        this.isCvmRequired = isCvmRequired;
        this.isOfflinePinSupported = isOfflinePinSupported;
        this.isIssuerUpdateProcessingSupported = isIssuerUpdateProcessingSupported;
        this.isConsumerDeviceCvmSupported = isConsumerDeviceCvmSupported;
    }


    public byte[] toBytes() {
        byte[] ret = new byte[4];

        if (isMagStripeSupported) {
            ret[0] = (byte) (ret[0] | 0b10000000);
        }

        if (isEmvModeSupported) {
            ret[0] = (byte) (ret[0] | 0b00100000);
        }

        if (isEmvContactChipSupported) {
            ret[0] = (byte) (ret[0] | 0b00010000);
        }

        if (isOfflineOnlyReader) {
            ret[0] = (byte) (ret[0] | 0b00001000);
        }

        if (isOnlinePinSupported) {
            ret[0] = (byte) (ret[0] | 0b00000100);
        }

        if (isSignatureSupported) {
            ret[0] = (byte) (ret[0] | 0b00000010);
        }

        if (isOfflineDataAuthenticationForOnlineAuthorizationsSupported) {
            ret[0] = (byte) (ret[0] | 0b00000001);
        }

        if (isOnlineCryptogramRequired) {
            ret[1] = (byte) (ret[1] | 0b10000000);
        }

        if (isCvmRequired) {
            ret[1] = (byte) (ret[1] | 0b01000000);
        }

        if (isOfflinePinSupported) {
            ret[1] = (byte) (ret[1] | 0b00100000);
        }

        if (isIssuerUpdateProcessingSupported) {
            ret[2] = (byte) (ret[2] | 0b10000000);
        }

        if (isConsumerDeviceCvmSupported) {
            ret[2] = (byte) (ret[2] | 0b01000000);
        }

        return ret;
    }
}
