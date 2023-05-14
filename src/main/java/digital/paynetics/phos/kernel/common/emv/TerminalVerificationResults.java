package digital.paynetics.phos.kernel.common.emv;

/**
 * As in Book 3, C5 and Book C-2, A.1.169
 */
public class TerminalVerificationResults {
    // byte 1
    private boolean offlineDataAuthenticationNotPerformed;
    private boolean sdaFailed;
    private boolean iccDataMissing;
    private boolean cardOnTerminalExceptionFile;
    private boolean ddaFailed;
    private boolean cdaFailed;


    // byte 2
    private boolean iccAndTerminalAppDifferentVersions;
    private boolean expiredApplication;
    private boolean applicationNotYetEffective;
    private boolean requestedServiceNotAllowed;
    private boolean newCard;

    // byte 3
    private boolean cvmNotSuccessful;
    private boolean unrecognizedCvm;
    private boolean pinTryLimitExceeded;
    private boolean pinPadNotPresentOrWorking; // PIN entry required and PIN pad not present or not working
    private boolean pinNotEntered; // PIN entry required, PIN pad present, but PIN was not entered
    private boolean onlinePinEntered;

    // byte 4
    private boolean transactionExceedsFloorLimit;
    private boolean lowerConsecutiveOfflineLimitExceeded;
    private boolean upperConsecutiveOfflineLimitExceeded;
    private boolean transactionSelectedRandomlyForOnlineProcessing;
    private boolean merchantForcedTransactionOnline;

    // byte 5
    private boolean defaultTdolUsed;
    private boolean issuerAuthenticationFailed;
    private boolean scriptProcessingFailedBeforeFinalGenerateAc;
    private boolean scriptProcessingFailedAfterFinalGenerateAc;
    private boolean relayResistanceThresholdExceeded;
    private boolean relayResistanceTimeLimitsExceeded;
    private RelayResistancePerformed relayResistancePerformed = RelayResistancePerformed.NOT_SUPPORTED;


    public boolean isOfflineDataAuthenticationNotPerformed() {
        return offlineDataAuthenticationNotPerformed;
    }


    public void setOfflineDataAuthenticationNotPerformed(boolean offlineDataAuthenticationNotPerformed) {
        this.offlineDataAuthenticationNotPerformed = offlineDataAuthenticationNotPerformed;
    }


    public boolean isSdaFailed() {
        return sdaFailed;
    }


    public void setSdaFailed(boolean sdaFailed) {
        this.sdaFailed = sdaFailed;
    }


    public boolean isIccDataMissing() {
        return iccDataMissing;
    }


    public void setIccDataMissing(boolean iccDataMissing) {
        this.iccDataMissing = iccDataMissing;
    }


    public boolean isCardOnTerminalExceptionFile() {
        return cardOnTerminalExceptionFile;
    }


    public void setCardOnTerminalExceptionFile(boolean cardOnTerminalExceptionFile) {
        this.cardOnTerminalExceptionFile = cardOnTerminalExceptionFile;
    }


    public boolean isDdaFailed() {
        return ddaFailed;
    }


    public void setDdaFailed(boolean ddaFailed) {
        this.ddaFailed = ddaFailed;
    }


    public boolean isCdaFailed() {
        return cdaFailed;
    }


    public void setCdaFailed(boolean cdaFailed) {
        this.cdaFailed = cdaFailed;
    }


    public boolean isIccAndTerminalAppDifferentVersions() {
        return iccAndTerminalAppDifferentVersions;
    }


    public void setIccAndTerminalAppDifferentVersions(boolean iccAndTerminalAppDifferentVersions) {
        this.iccAndTerminalAppDifferentVersions = iccAndTerminalAppDifferentVersions;
    }


    public boolean isExpiredApplication() {
        return expiredApplication;
    }


    public void setExpiredApplication(boolean expiredApplication) {
        this.expiredApplication = expiredApplication;
    }


    public boolean isApplicationNotYetEffective() {
        return applicationNotYetEffective;
    }


    public void setApplicationNotYetEffective(boolean applicationNotYetEffective) {
        this.applicationNotYetEffective = applicationNotYetEffective;
    }


    public boolean isRequestedServiceNotAllowed() {
        return requestedServiceNotAllowed;
    }


    public void setRequestedServiceNotAllowed(boolean requestedServiceNotAllowed) {
        this.requestedServiceNotAllowed = requestedServiceNotAllowed;
    }


    public boolean isNewCard() {
        return newCard;
    }


    public void setNewCard(boolean newCard) {
        this.newCard = newCard;
    }


    public boolean isCvmNotSuccessful() {
        return cvmNotSuccessful;
    }


    public void setCvmNotSuccessful(boolean cvmNotSuccessful) {
        this.cvmNotSuccessful = cvmNotSuccessful;
    }


    public boolean isUnrecognizedCvm() {
        return unrecognizedCvm;
    }


    public void setUnrecognizedCvm(boolean unrecognizedCvm) {
        this.unrecognizedCvm = unrecognizedCvm;
    }


    public boolean isPinTryLimitExceeded() {
        return pinTryLimitExceeded;
    }


    public void setPinTryLimitExceeded(boolean pinTryLimitExceeded) {
        this.pinTryLimitExceeded = pinTryLimitExceeded;
    }


    public boolean isPinPadNotPresentOrWorking() {
        return pinPadNotPresentOrWorking;
    }


    public void setPinPadNotPresentOrWorking(boolean pinPadNotPresentOrWorking) {
        this.pinPadNotPresentOrWorking = pinPadNotPresentOrWorking;
    }


    public boolean isPinNotEntered() {
        return pinNotEntered;
    }


    public void setPinNotEntered(boolean pinNotEntered) {
        this.pinNotEntered = pinNotEntered;
    }


    public boolean isOnlinePinEntered() {
        return onlinePinEntered;
    }


    public void setOnlinePinEntered(boolean onlinePinEntered) {
        this.onlinePinEntered = onlinePinEntered;
    }


    public boolean isTransactionExceedsFloorLimit() {
        return transactionExceedsFloorLimit;
    }


    public void setTransactionExceedsFloorLimit(boolean transactionExceedsFloorLimit) {
        this.transactionExceedsFloorLimit = transactionExceedsFloorLimit;
    }


    public boolean isLowerConsecutiveOfflineLimitExceeded() {
        return lowerConsecutiveOfflineLimitExceeded;
    }


    public void setLowerConsecutiveOfflineLimitExceeded(boolean lowerConsecutiveOfflineLimitExceeded) {
        this.lowerConsecutiveOfflineLimitExceeded = lowerConsecutiveOfflineLimitExceeded;
    }


    public boolean isUpperConsecutiveOfflineLimitExceeded() {
        return upperConsecutiveOfflineLimitExceeded;
    }


    public void setUpperConsecutiveOfflineLimitExceeded(boolean upperConsecutiveOfflineLimitExceeded) {
        this.upperConsecutiveOfflineLimitExceeded = upperConsecutiveOfflineLimitExceeded;
    }


    public boolean isTransactionSelectedRandomlyForOnlineProcessing() {
        return transactionSelectedRandomlyForOnlineProcessing;
    }


    public void setTransactionSelectedRandomlyForOnlineProcessing(boolean transactionSelectedRandomlyForOnlineProcessing) {
        this.transactionSelectedRandomlyForOnlineProcessing = transactionSelectedRandomlyForOnlineProcessing;
    }


    public boolean isMerchantForcedTransactionOnline() {
        return merchantForcedTransactionOnline;
    }


    public void setMerchantForcedTransactionOnline(boolean merchantForcedTransactionOnline) {
        this.merchantForcedTransactionOnline = merchantForcedTransactionOnline;
    }


    public boolean isDefaultTdolUsed() {
        return defaultTdolUsed;
    }


    public void setDefaultTdolUsed(boolean defaultTdolUsed) {
        this.defaultTdolUsed = defaultTdolUsed;
    }


    public boolean isIssuerAuthenticationFailed() {
        return issuerAuthenticationFailed;
    }


    public void setIssuerAuthenticationFailed(boolean issuerAuthenticationFailed) {
        this.issuerAuthenticationFailed = issuerAuthenticationFailed;
    }


    public boolean isScriptProcessingFailedBeforeFinalGenerateAc() {
        return scriptProcessingFailedBeforeFinalGenerateAc;
    }


    public void setScriptProcessingFailedBeforeFinalGenerateAc(boolean scriptProcessingFailedBeforeFinalGenerateAc) {
        this.scriptProcessingFailedBeforeFinalGenerateAc = scriptProcessingFailedBeforeFinalGenerateAc;
    }


    public boolean isScriptProcessingFailedAfterFinalGenerateAc() {
        return scriptProcessingFailedAfterFinalGenerateAc;
    }


    public void setScriptProcessingFailedAfterFinalGenerateAc(boolean scriptProcessingFailedAfterFinalGenerateAc) {
        this.scriptProcessingFailedAfterFinalGenerateAc = scriptProcessingFailedAfterFinalGenerateAc;
    }


    public boolean isRelayResistanceThresholdExceeded() {
        return relayResistanceThresholdExceeded;
    }


    public void setRelayResistanceThresholdExceeded(boolean relayResistanceThresholdExceeded) {
        this.relayResistanceThresholdExceeded = relayResistanceThresholdExceeded;
    }


    public boolean isRelayResistanceTimeLimitsExceeded() {
        return relayResistanceTimeLimitsExceeded;
    }


    public void setRelayResistanceTimeLimitsExceeded(boolean relayResistanceTimeLimitsExceeded) {
        this.relayResistanceTimeLimitsExceeded = relayResistanceTimeLimitsExceeded;
    }


    public RelayResistancePerformed getRelayResistancePerformed() {
        return relayResistancePerformed;
    }


    public void setRelayResistancePerformed(RelayResistancePerformed relayResistancePerformed) {
        this.relayResistancePerformed = relayResistancePerformed;
    }


    public enum RelayResistancePerformed {
        NOT_SUPPORTED,
        NOT_PERFORMED,
        PERFORMED
    }


    public byte[] toBytes() {
        byte[] ret = new byte[5];

        if (offlineDataAuthenticationNotPerformed) {
            ret[0] = (byte) (ret[0] | 0b10000000);
        }

        if (sdaFailed) {
            ret[0] = (byte) (ret[0] | 0b01000000);
        }

        if (iccDataMissing) {
            ret[0] = (byte) (ret[0] | 0b00100000);
        }

        if (cardOnTerminalExceptionFile) {
            ret[0] = (byte) (ret[0] | 0b00010000);
        }

        if (ddaFailed) {
            ret[0] = (byte) (ret[0] | 0b00001000);
        }

        if (cdaFailed) {
            ret[0] = (byte) (ret[0] | 0b00000100);
        }


        if (iccAndTerminalAppDifferentVersions) {
            ret[1] = (byte) (ret[1] | 0b10000000);
        }

        if (expiredApplication) {
            ret[1] = (byte) (ret[1] | 0b01000000);
        }

        if (applicationNotYetEffective) {
            ret[1] = (byte) (ret[1] | 0b00100000);
        }

        if (requestedServiceNotAllowed) {
            ret[1] = (byte) (ret[1] | 0b00010000);
        }

        if (newCard) {
            ret[1] = (byte) (ret[1] | 0b00001000);
        }

        if (cvmNotSuccessful) {
            ret[2] = (byte) (ret[2] | 0b10000000);
        }

        if (unrecognizedCvm) {
            ret[2] = (byte) (ret[2] | 0b01000000);
        }

        if (pinTryLimitExceeded) {
            ret[2] = (byte) (ret[2] | 0b00100000);
        }

        if (pinPadNotPresentOrWorking) {
            ret[2] = (byte) (ret[2] | 0b00010000);
        }

        if (pinNotEntered) {
            ret[2] = (byte) (ret[2] | 0b00001000);
        }

        if (onlinePinEntered) {
            ret[2] = (byte) (ret[2] | 0b00000100);
        }

        if (transactionExceedsFloorLimit) {
            ret[3] = (byte) (ret[3] | 0b10000000);
        }

        if (lowerConsecutiveOfflineLimitExceeded) {
            ret[3] = (byte) (ret[3] | 0b01000000);
        }

        if (upperConsecutiveOfflineLimitExceeded) {
            ret[3] = (byte) (ret[3] | 0b00100000);
        }

        if (transactionSelectedRandomlyForOnlineProcessing) {
            ret[3] = (byte) (ret[3] | 0b00010000);
        }

        if (merchantForcedTransactionOnline) {
            ret[3] = (byte) (ret[3] | 0b00001000);
        }

        if (defaultTdolUsed) {
            ret[4] = (byte) (ret[4] | 0b10000000);
        }

        if (issuerAuthenticationFailed) {
            ret[4] = (byte) (ret[4] | 0b01000000);
        }

        if (scriptProcessingFailedBeforeFinalGenerateAc) {
            ret[4] = (byte) (ret[4] | 0b00100000);
        }

        if (scriptProcessingFailedAfterFinalGenerateAc) {
            ret[4] = (byte) (ret[4] | 0b00010000);
        }

        if (relayResistanceThresholdExceeded) {
            ret[4] = (byte) (ret[4] | 0b00001000);
        }

        if (relayResistanceTimeLimitsExceeded) {
            ret[4] = (byte) (ret[4] | 0b00000100);
        }

        switch (relayResistancePerformed) {
            case NOT_SUPPORTED:
                // skip, zeros
                break;
            case NOT_PERFORMED:
                ret[4] = (byte) (ret[4] | 0b00000001);
                break;
            case PERFORMED:
                ret[4] = (byte) (ret[4] | 0b00000010);
                break;
        }

        return ret;
    }
}
