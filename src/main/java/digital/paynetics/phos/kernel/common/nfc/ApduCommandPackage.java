package digital.paynetics.phos.kernel.common.nfc;

public class ApduCommandPackage {
    private final byte cla;
    private final byte ins;
    private final byte p1;
    private final byte p2;
    private final int lc;
    private final byte[] data;
    private final int le;


    public ApduCommandPackage(final ApduCommand sw, final byte p1, final byte p2, final byte[] data, final int le) {

        cla = sw.cla;
        ins = sw.ins;
        this.p1 = p1;
        this.p2 = p2;
        lc = (data == null ? 0 : data.length);
        this.data = data;
        this.le = le;

    }


    public ApduCommandPackage(final ApduCommand sw, final byte[] data, final int le) {
        if (le < 0) {
            throw new IllegalArgumentException("le < 0: " + le);
        }
        cla = sw.cla;
        ins = sw.ins;
        p1 = sw.p1;
        p2 = sw.p2;
        lc = data == null ? 0 : data.length;
        this.data = data;
        this.le = le;
    }


    public ApduCommandPackage(final ApduCommand sw, final byte[] data) {
        this(sw, data, 0);
    }


    public ApduCommandPackage(final ApduCommand sw) {
        this(sw, null, 0);
    }


    public byte[] toBytes() {
        int length = 4; // CLA, INS, P1, p2
        if (data != null && data.length != 0) {
            length += 1; // LC
            length += data.length; // DATA
        }
        if (le >= 0) {
            length += 1; // LE
        }

        final byte[] apdu = new byte[length];

        int index = 0;
        apdu[index] = cla;
        index++;
        apdu[index] = ins;
        index++;
        apdu[index] = p1;
        index++;
        apdu[index] = p2;
        index++;
        if (data != null && data.length != 0) {
            apdu[index] = (byte) lc;
            index++;
            System.arraycopy(data, 0, apdu, index, data.length);
            index += data.length;
        }
        if (le >= 0) {
            if (le <= 255) {
                apdu[index] = (byte) le; // LE
            } else {
                throw new IllegalArgumentException("Invalid le: " + le);
            }
        }

        return apdu;
    }


    public byte getCla() {
        return cla;
    }


    public byte getIns() {
        return ins;
    }
}
