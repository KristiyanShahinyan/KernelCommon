package digital.paynetics.phos.kernel.common.emv;

import digital.paynetics.phos.kernel.common.misc.TtqConfiguration;


/**
 * Terminal Transaction Qualifiers, as described in Book A, table 5-4.
 * Used in pre-processing as described in Book B (Version 2.6), 3.1.1 Pre-Processing Requirements
 * This class contains only methods to raise the isOnlineCryptogramRequired and isCvmRequired flags because there is
 * no clearing them defined in the specs.
 */
public class TtqPreProcessing extends TtqConfiguration {
    private boolean isOnlineCryptogramRequired;

    private boolean isCvmRequired;


    public TtqPreProcessing(TtqConfiguration ttq) {
        super(ttq);
        isOnlineCryptogramRequired = false;
        isCvmRequired = false;
    }


    public void raiseOnlineCryptogramRequiredFlag() {
        isOnlineCryptogramRequired = true;
    }


    public void raiseCvmRequiredFlag() {
        isCvmRequired = true;
    }


    public boolean isOnlineCryptogramRequired() {
        return isOnlineCryptogramRequired;
    }


    public boolean isCvmRequired() {
        return isCvmRequired;
    }


    public TtqFinal getFinal() {
        return new TtqFinal(isMagStripeSupported, isEmvModeSupported, isEmvContactChipSupported,
                isOfflineOnlyReader, isOnlinePinSupported, isSignatureSupported,
                isOfflineDataAuthenticationForOnlineAuthorizationsSupported, isOnlineCryptogramRequired,
                isCvmRequired, isOfflinePinSupported, isIssuerUpdateProcessingSupported,
                isConsumerDeviceCvmSupported);
    }
}
