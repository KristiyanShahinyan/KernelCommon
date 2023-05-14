package digital.paynetics.phos.kernel.common.emv.kernel.common;

public enum ApplicationCryptogramType {
    AAC,
    TC,
    ARQC,
    UNKNOWN;


    public static ApplicationCryptogramType resolveType(byte cid) {
        int test = cid & 0b11000000;
        if (test == 0b10000000) {
            return ApplicationCryptogramType.ARQC;
        } else if (test == 0b01000000) {
            return ApplicationCryptogramType.TC;
        } else if (test == 0) {
            return ApplicationCryptogramType.AAC;
        } else {
            return ApplicationCryptogramType.UNKNOWN;
        }
    }
}
