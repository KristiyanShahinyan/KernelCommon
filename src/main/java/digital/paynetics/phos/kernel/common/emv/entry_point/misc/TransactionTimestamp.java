package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import hirondelle.date4j.DateTime;


public final class TransactionTimestamp {
    @SuppressWarnings("SpellCheckingInspection")
    private static final String DATE_FORMAT = "YYMMDD";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String TIME_FORMAT = "hhmmss";

    private final DateTime ts;


    public TransactionTimestamp(DateTime ts) {
        this.ts = ts;
    }


    public DateTime getTs() {
        return ts;
    }


    public byte[] getDateBytes() {
        return ByteUtils.fromString(ts.format(DATE_FORMAT));
    }


    public byte[] getTimeBytes() {
        return ByteUtils.fromString(ts.format(TIME_FORMAT));
    }
}
