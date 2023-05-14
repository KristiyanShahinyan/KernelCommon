package digital.paynetics.phos.kernel.common.emv.ui;

public enum StandardMessages {
    APPROVED((byte) 0x03),
    NOT_AUTHORIZED((byte) 0x07), // DECLINED
    PLEASE_ENTER_YOUR_PIN((byte) 0x09),
    PROCESSING_ERROR((byte) 0x0F),
    REMOVE_CARD((byte) 0x10),
    WELCOME((byte) 0x14),
    PRESENT_CARD((byte) 0x15),
    PROCESSING((byte) 0x16),
    CARD_READ_OK_REMOVE_CARD((byte) 0x17),
    INSERT_OR_SWIPE_CARD((byte) 0x18),
    PRESENT_ONE_CARD_ONLY((byte) 0x19),
    APPROVED_SIGN((byte) 0x1a),
    AUTHORIZING((byte) 0x1b),
    TRY_ANOTHER_CARD((byte) 0x1c), // ERROR - TRY ANOTHER
    INSERT_CARD((byte) 0x1d),
    CLEAR_DISPLAY((byte) 0x1e),
    SEE_PHONE_FOR_INSTRUCTIONS((byte) 0x20),
    PRESENT_CARD_AGAIN((byte) 0x21),
    NO_MESSAGE((byte) 0xff);

    private final byte code;


    StandardMessages(byte code) {
        this.code = code;
    }


    public byte getCode() {
        return code;
    }
}
