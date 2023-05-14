package digital.paynetics.phos.kernel.common.nfc;

public enum ApduCommand {

    /**
     * Select command
     */
    SELECT(0x00, 0xA4, 0x04, 0x00),

    /**
     * Read record command
     */
    READ_RECORD(0x00, 0xB2, 0x00, 0x00),

    /**
     * GPO Command
     */
    GPO(0x80, 0xA8, 0x00, 0x00),

    /**
     * Get Data Command
     */
    GET_DATA(0x80, 0xCA, 0x00, 0x00),

    /**
     * Put Data Command
     */
    PUT_DATA(0x80, 0xDA, 0x00, 0x00),


    /**
     * Generate Application Cryptogram command
     */
    GENERATE_APPLICATION_CRYPTOGRAM(0x80, 0xAE, 0x00, 0x00),


    /**
     * Recover Application Cryptogram command
     */
    RECOVER_APPLICATION_CRYPTOGRAM(0x80, 0xD0, 0x00, 0x00),


    /**
     * Exchange Relay Resistance Data command
     */
    EXCHANGE_RELAY_RESISTANCE_DATA(0x80, 0xEA, 0x00, 0x00),

    /**
     * Compute Relay Resistance Data command
     */
    COMPUTE_CRYPTOGRAPHIC_CHECKSUM(0x80, 0x2a, 0x8e, 0x80);
    /**
     * Class byte
     */
    public final byte cla;

    /**
     * Instruction byte
     */
    public final byte ins;

    /**
     * Parameter 1 byte
     */
    public final byte p1;

    /**
     * Parameter 2 byte
     */
    public final byte p2;


    /**
     * Constructor using field
     *
     * @param cla class
     * @param ins instruction
     * @param p1  parameter 1
     * @param p2  parameter 2
     */
    ApduCommand(final int cla, final int ins, final int p1, final int p2) {
        this.cla = (byte) cla;
        this.ins = (byte) ins;
        this.p1 = (byte) p1;
        this.p2 = (byte) p2;
    }
}
