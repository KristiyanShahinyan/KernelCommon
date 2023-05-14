package digital.paynetics.phos.kernel.common.emv.kernel.common;

public enum KernelType {
    JCB_VISA(1),
    MASTERCARD(2),
    VISA(3),
    AMERICAN_EXPRESS(4),
    JCB(5),
    DISCOVER(6),
    UNIONPAY(7);

    private final int emvKernelIdentifier;


    KernelType(final int emvKernelIdentifier) {
        this.emvKernelIdentifier = emvKernelIdentifier;
    }


    public static KernelType valueOf(final int id) {
        switch (id) {
            case 1:
                return JCB_VISA;
            case 2:
                return MASTERCARD;
            case 3:
                return VISA;
            case 4:
                return AMERICAN_EXPRESS;
            case 5:
                return JCB;
            case 6:
                return DISCOVER;
            case 7:
                return UNIONPAY;
            default:
                return null;
        }
    }


    public int getEmvKernelIdentifier() {
        return emvKernelIdentifier;
    }
}
