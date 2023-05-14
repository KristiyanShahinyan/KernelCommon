package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.math.BigInteger;

import digital.paynetics.phos.exceptions.EmvException;


/**
 * Reflects EMV Book 3, Annex C.2
 */
public final class ApplicationUsageControl {
    private final boolean isValidForDomesticCash;
    private final boolean isValidForInternationalCash;
    private final boolean isValidForDomesticGoods;
    private final boolean isValidForInternationalGoods;
    private final boolean isValidForDomesticServices;
    private final boolean isValidForInternationalServices;
    private final boolean isValidAtAtms;
    private final boolean isValidAtOtherThanAtms;

    private final boolean isDomesticCashbackAllowed;
    private final boolean isInternationalCashbackAllowed;


    public ApplicationUsageControl(boolean isValidForDomesticCash, boolean isValidForInternationalCash,
                                   boolean isValidForDomesticGoods, boolean isValidForInternationalGoods,
                                   boolean isValidForDomesticServices, boolean isValidForInternationalServices,
                                   boolean isValidAtAtms, boolean isValidAtOtherThanAtms,
                                   boolean isDomesticCashbackAllowed, boolean isInternationalCashbackAllowed) {

        this.isValidForDomesticCash = isValidForDomesticCash;
        this.isValidForInternationalCash = isValidForInternationalCash;
        this.isValidForDomesticGoods = isValidForDomesticGoods;
        this.isValidForInternationalGoods = isValidForInternationalGoods;
        this.isValidForDomesticServices = isValidForDomesticServices;
        this.isValidForInternationalServices = isValidForInternationalServices;
        this.isValidAtAtms = isValidAtAtms;
        this.isValidAtOtherThanAtms = isValidAtOtherThanAtms;

        this.isDomesticCashbackAllowed = isDomesticCashbackAllowed;
        this.isInternationalCashbackAllowed = isInternationalCashbackAllowed;
    }


    public ApplicationUsageControl(byte[] data) throws EmvException {
        if (data.length != 2) {
            throw new EmvException("Data length must be 2");
        }

        BigInteger bi1 = BigInteger.valueOf(data[0]);
        isValidForDomesticCash = bi1.testBit(8 - 1);
        isValidForInternationalCash = bi1.testBit(7 - 1);
        isValidForDomesticGoods = bi1.testBit(6 - 1);
        isValidForInternationalGoods = bi1.testBit(5 - 1);
        isValidForDomesticServices = bi1.testBit(4 - 1);
        isValidForInternationalServices = bi1.testBit(3 - 1);
        isValidAtAtms = bi1.testBit(2 - 1);
        //noinspection PointlessArithmeticExpression
        isValidAtOtherThanAtms = bi1.testBit(1 - 1);

        BigInteger bi2 = BigInteger.valueOf(data[1]);
        isDomesticCashbackAllowed = bi2.testBit(8 - 1);
        isInternationalCashbackAllowed = bi2.testBit(7 - 1);
    }


    public boolean isValidForDomesticCash() {
        return isValidForDomesticCash;
    }


    public boolean isValidForInternationalCash() {
        return isValidForInternationalCash;
    }


    public boolean isValidForDomesticGoods() {
        return isValidForDomesticGoods;
    }


    public boolean isValidForInternationalGoods() {
        return isValidForInternationalGoods;
    }


    public boolean isValidForDomesticServices() {
        return isValidForDomesticServices;
    }


    public boolean isValidForInternationalServices() {
        return isValidForInternationalServices;
    }


    public boolean isValidAtAtms() {
        return isValidAtAtms;
    }


    public boolean isValidAtOtherThanAtms() {
        return isValidAtOtherThanAtms;
    }


    public boolean isDomesticCashbackAllowed() {
        return isDomesticCashbackAllowed;
    }


    public boolean isInternationalCashbackAllowed() {
        return isInternationalCashbackAllowed;
    }
}
