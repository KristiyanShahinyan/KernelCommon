package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

/**
 * Issuer Code Table Index as specified in EMV 4.3 Book 3, C4
 */
public enum IssuerCodeTableIndex {
    PART1(1, "ISO-8859-1"),
    PART2(2, "ISO-8859-2"),
    PART3(3, "ISO-8859-3"),
    PART4(4, "ISO-8859-4"),
    PART5(5, "ISO-8859-5"),
    PART6(6, "ISO-8859-6"),
    PART7(7, "ISO-8859-7"),
    PART8(8, "ISO-8859-8"),
    PART9(9, "ISO-8859-9"),
    PART10(10, "ISO-8859-10");

    private final int index;
    private final String codeTableName;


    IssuerCodeTableIndex(int index, String codeTableName) {
        this.index = index;
        this.codeTableName = codeTableName;
    }


    public static IssuerCodeTableIndex valueOf(int index) {
        switch (index) {
            case 1:
                return PART1;
            case 2:
                return PART2;
            case 3:
                return PART3;
            case 4:
                return PART4;
            case 5:
                return PART5;
            case 6:
                return PART6;
            case 7:
                return PART7;
            case 8:
                return PART8;
            case 9:
                return PART9;
            case 10:
                return PART10;
            default:
                return null;
        }
    }


    public int getIndex() {
        return index;
    }


    public String getCodeTableName() {
        return codeTableName;
    }
}
