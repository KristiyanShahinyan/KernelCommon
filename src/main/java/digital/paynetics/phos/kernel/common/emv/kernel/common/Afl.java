package digital.paynetics.phos.kernel.common.emv.kernel.common;

import digital.paynetics.phos.exceptions.EmvException;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


/**
 * Class used to describe Application file locator
 */
public final class Afl {

    /**
     * SFI
     */
    private final int sfi;

    /**
     * record
     */
    private final int firstRecord;

    /**
     * Last record
     */
    private final int lastRecord;

    /**
     * Offline authentication
     */
    private final int recordsInvolvedInDataAuthentication;


    public Afl(int sfi, int firstRecord, int lastRecord, int recordsInvolvedInDataAuthentication) throws EmvException {
        if (firstRecord <= 0) {
            throw new EmvException("Invalid firstRecord value: " + firstRecord);
        }

        if (lastRecord <= 0) {
            throw new EmvException("Invalid lastRecord value: " + lastRecord);
        }

        if (lastRecord < firstRecord) {
            throw new EmvException("last record must be equal or grater than firstRecord");
        }

        if (recordsInvolvedInDataAuthentication < 0 ||
                recordsInvolvedInDataAuthentication > lastRecord - firstRecord + 1) {

            throw new EmvException("Invalid recordsInvolvedInDataAuthentication value: " +
                    recordsInvolvedInDataAuthentication);
        }


        this.sfi = sfi;
        this.firstRecord = firstRecord;
        this.lastRecord = lastRecord;
        this.recordsInvolvedInDataAuthentication = recordsInvolvedInDataAuthentication;
    }


    /**
     * Method used to get the field sfi
     *
     * @return the sfi
     */
    public int getSfi() {
        return sfi;
    }


    /**
     * Method used to get the field firstRecord
     *
     * @return the firstRecord
     */
    public int getFirstRecord() {
        return firstRecord;
    }


    /**
     * Method used to get the field lastRecord
     *
     * @return the lastRecord
     */
    public int getLastRecord() {
        return lastRecord;
    }


    public int getRecordsInvolvedInDataAuthentication() {
        return recordsInvolvedInDataAuthentication;
    }


    @Override
    public String toString() {
        return format("sfi: {}, firstRecord: {}, lastRecord: {}, recordInvolvedInDataAuthentication: {}", sfi, firstRecord,
                lastRecord, recordsInvolvedInDataAuthentication);
    }
}
