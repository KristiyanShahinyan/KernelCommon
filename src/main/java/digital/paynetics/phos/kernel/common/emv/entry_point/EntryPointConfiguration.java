package digital.paynetics.phos.kernel.common.emv.entry_point;


import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import digital.paynetics.phos.kernel.common.misc.TtqConfiguration;


public final class EntryPointConfiguration {
    @SerializedName("status_check_supported")
    public final boolean statusCheckSupported;
    @SerializedName("zero_amount_allowed")
    public final boolean zeroAmountAllowed;
    @SerializedName("reader_contactless_transaction_limit")
    public final int readerContactlessTransactionLimit;
    @SerializedName("reared_contactless_floor_limit")
    public final int readerContactlessFloorLimit;
    @SerializedName("terminal_floor_limit")
    public final int terminalFloorLimit;
    @SerializedName("reader_cmv_required_limit")
    public final int readerCvmRequiredLimit;
    @SerializedName("extended_selection_supported")
    public final boolean extendedSelectionSupported;
    @SerializedName("ttq")
    public final TtqConfiguration ttq;


    public EntryPointConfiguration(boolean statusCheckSupported,
                                   boolean zeroAmountAllowed,
                                   int readerContactlessTransactionLimit,
                                   int readerContactlessFloorLimit,
                                   int terminalFloorLimit,
                                   int readerCvmRequiredLimit,
                                   boolean extendedSelectionSupported,
                                   TtqConfiguration ttq) {

        this.statusCheckSupported = statusCheckSupported;
        this.zeroAmountAllowed = zeroAmountAllowed;
        this.readerContactlessTransactionLimit = readerContactlessTransactionLimit;
        this.readerContactlessFloorLimit = readerContactlessFloorLimit;
        this.terminalFloorLimit = terminalFloorLimit;
        this.readerCvmRequiredLimit = readerCvmRequiredLimit;
        this.extendedSelectionSupported = extendedSelectionSupported;
        this.ttq = ttq;
    }


    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof EntryPointConfiguration)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        final EntryPointConfiguration other = (EntryPointConfiguration) obj;

        return this.statusCheckSupported == other.statusCheckSupported &&
                this.zeroAmountAllowed == other.zeroAmountAllowed &&
                this.readerContactlessTransactionLimit == other.readerContactlessTransactionLimit &&
                this.readerContactlessFloorLimit == other.readerContactlessFloorLimit &&
                this.terminalFloorLimit == other.terminalFloorLimit &&
                this.readerCvmRequiredLimit == other.readerCvmRequiredLimit &&
                this.extendedSelectionSupported == other.extendedSelectionSupported &&
                (ttq == null || this.ttq.equals(other.ttq));
    }


    @Override
    public int hashCode() {
        return Objects.hash(statusCheckSupported, zeroAmountAllowed, readerContactlessTransactionLimit,
                readerContactlessFloorLimit, terminalFloorLimit, readerCvmRequiredLimit,
                extendedSelectionSupported, ttq);
    }


    @Override
    public String toString() {
        return "statusCheckSupported: " + statusCheckSupported +
                ", zeroAmountAllowed: " + zeroAmountAllowed +
                ", readerContactlessTransactionLimit: " + readerContactlessTransactionLimit +
                ", readerContactlessFloorLimit: " + readerContactlessFloorLimit +
                ", terminalFloorLimit: " + terminalFloorLimit +
                ", readerCvmRequiredLimit: " + readerCvmRequiredLimit +
                ", extendedSelectionSupported: " + extendedSelectionSupported +
                ", ttq: " + ttq;
    }
}
