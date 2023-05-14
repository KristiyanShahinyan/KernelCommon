package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.math.BigInteger;

import digital.paynetics.phos.exceptions.EmvException;


/**
 * Reflects CVM List rule as described in Book 3, section 10.5
 */
public final class CvmListRule {
    private final boolean failCvmIfUnsuccessful;

    private final byte cvmCodeRaw;
    private final CvmCode cvmCode;
    private final ConditionCode conditionCode;


    public CvmListRule(boolean failCvmIfUnsuccessful, byte cvmCodeRaw, CvmCode cvmCode, ConditionCode conditionCode) {
        this.failCvmIfUnsuccessful = failCvmIfUnsuccessful;
        this.cvmCodeRaw = cvmCodeRaw;
        this.cvmCode = cvmCode;
        this.conditionCode = conditionCode;
    }


    public CvmListRule(byte[] data) throws EmvException {
        if (data.length != 2) {
            throw new EmvException("Data length must be 2");
        }

        BigInteger bi1 = BigInteger.valueOf(data[0]);
        failCvmIfUnsuccessful = !bi1.testBit(7 - 1);

        // clearing bit 7 and 8 in order to have only method bits
        byte cvmCodeOnly = (byte) (data[0] & (byte) 0b00111111);

        cvmCodeRaw = data[0];

        switch (cvmCodeOnly) {
            case 0:
                cvmCode = CvmCode.FAIL;
                break;
            case 1:
                cvmCode = CvmCode.PLAINTEXT;
                break;
            case 2:
                cvmCode = CvmCode.ENCIPHERED_ONLINE;
                break;
            case 3:
                cvmCode = CvmCode.PLAINTEXT_AND_SIGNATURE;
                break;
            case 4:
                cvmCode = CvmCode.ENCIPHERED_BY_CARD;
                break;
            case 5:
                cvmCode = CvmCode.ENCIPHERED_BY_CARD_AND_SIGNATURE;
                break;
            case 30:
                cvmCode = CvmCode.SIGNATURE;
                break;
            case 31:
                cvmCode = CvmCode.NO_CVM_REQUIRED;
                break;
            default:
                cvmCode = CvmCode.RFU;
                break;
        }


        switch (data[1]) {
            case 0:
                conditionCode = ConditionCode.ALWAYS;
                break;
            case 1:
                conditionCode = ConditionCode.UNATTENDED_CASH;
                break;
            case 2:
                conditionCode = ConditionCode.NOT_ALL_CASH_NOT_CASHBACK;
                break;
            case 3:
                conditionCode = ConditionCode.IF_TERMINAL_SUPPORTS;
                break;
            case 4:
                conditionCode = ConditionCode.MANUAL_CASH;
                break;
            case 5:
                conditionCode = ConditionCode.PURCHASE_WITH_CASHBACK;
                break;
            case 6:
                conditionCode = ConditionCode.APP_CURRENCY_UNDER_X;
                break;
            case 7:
                conditionCode = ConditionCode.APP_CURRENCY_OVER_X;
                break;
            case 8:
                conditionCode = ConditionCode.APP_CURRENCY_UNDER_Y;
                break;
            case 9:
                conditionCode = ConditionCode.APP_CURRENCY_OVER_Y;
                break;
            default:
                conditionCode = ConditionCode.UNKNOWN;
                break;
        }
    }


    public boolean isFailCvmIfUnsuccessful() {
        return failCvmIfUnsuccessful;
    }


    public CvmCode getCvmCode() {
        return cvmCode;
    }


    public ConditionCode getConditionCode() {
        return conditionCode;
    }


    public byte getCvmCodeRaw() {
        return cvmCodeRaw;
    }


    public enum CvmCode {
        FAIL(0),
        PLAINTEXT(1),
        ENCIPHERED_ONLINE(2),
        PLAINTEXT_AND_SIGNATURE(3),
        ENCIPHERED_BY_CARD(4),
        ENCIPHERED_BY_CARD_AND_SIGNATURE(5),
        SIGNATURE(30),
        NO_CVM_REQUIRED(31),
        RFU(-1);


        private final int code;


        CvmCode(int code) {
            this.code = code;
        }


        public int getCode() {
            return code;
        }
    }


    public enum ConditionCode {
        ALWAYS(0),
        UNATTENDED_CASH(1),
        NOT_ALL_CASH_NOT_CASHBACK(2),
        IF_TERMINAL_SUPPORTS(3),
        MANUAL_CASH(4),
        PURCHASE_WITH_CASHBACK(5),
        APP_CURRENCY_UNDER_X(6),
        APP_CURRENCY_OVER_X(7),
        APP_CURRENCY_UNDER_Y(8),
        APP_CURRENCY_OVER_Y(9),
        UNKNOWN(-1);


        private final int code;


        ConditionCode(int code) {
            this.code = code;
        }


        public int getCode() {
            return code;
        }
    }
}
