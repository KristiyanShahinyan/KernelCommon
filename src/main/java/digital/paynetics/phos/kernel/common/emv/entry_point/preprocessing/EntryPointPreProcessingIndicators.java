package digital.paynetics.phos.kernel.common.emv.entry_point.preprocessing;

import digital.paynetics.phos.kernel.common.emv.TtqPreProcessing;
import java8.util.Optional;


/**
 * Entry point pre-processing indicators as described in Book A, table 5-3
 */
public class EntryPointPreProcessingIndicators {
    private boolean statusCheckRequested;
    private boolean contactlessApplicationNotAllowed;
    private boolean zeroAmount;
    private boolean readerCvmLimitExceeded;
    private boolean readerContactlessFloorLimitExceeded;
    private Optional<TtqPreProcessing> ttq = Optional.empty();


    public EntryPointPreProcessingIndicators() {
    }


    public boolean isStatusCheckRequested() {
        return statusCheckRequested;
    }


    public void raiseStatusCheckRequestedFlag() {
        this.statusCheckRequested = true;
    }


    public boolean isContactlessApplicationNotAllowed() {
        return contactlessApplicationNotAllowed;
    }


    public void raiseContactlessApplicationNotAllowedFlag() {
        this.contactlessApplicationNotAllowed = true;
    }


    public boolean isZeroAmount() {
        return zeroAmount;
    }


    public void raiseZeroAmountFlag() {
        this.zeroAmount = true;
    }


    public boolean isReaderCvmLimitExceeded() {
        return readerCvmLimitExceeded;
    }


    public void raiseReaderCvmLimitExceededFlag() {
        this.readerCvmLimitExceeded = true;
    }


    public boolean isReaderContactlessFloorLimitExceeded() {
        return readerContactlessFloorLimitExceeded;
    }


    public void raiseReaderContactlessFloorLimitExceededFlag() {
        this.readerContactlessFloorLimitExceeded = true;
    }


    public Optional<TtqPreProcessing> getTtq() {
        return ttq;
    }


    public void setTtq(TtqPreProcessing ttq) {
        if (this.ttq.isPresent()) {
            throw new IllegalStateException("TTQ already add");
        }
        this.ttq = Optional.of(ttq);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EntryPointPreProcessingIndicators)) {
            return false;
        }


        EntryPointPreProcessingIndicators other = (EntryPointPreProcessingIndicators) obj;

        return statusCheckRequested == other.statusCheckRequested &&
                contactlessApplicationNotAllowed == other.contactlessApplicationNotAllowed &&
                zeroAmount == other.zeroAmount &&
                readerCvmLimitExceeded == other.readerCvmLimitExceeded &&
                readerContactlessFloorLimitExceeded == other.readerContactlessFloorLimitExceeded &&
                ttq != null ? ttq.equals(other.ttq) : other.ttq == null;

    }
}
