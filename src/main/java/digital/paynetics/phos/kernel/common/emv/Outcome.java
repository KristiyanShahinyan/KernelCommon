package digital.paynetics.phos.kernel.common.emv;


import java.util.List;

import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.emv.ui.ContactlessTransactionStatus;
import digital.paynetics.phos.kernel.common.emv.ui.StandardMessages;
import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import java8.util.Optional;


@SuppressWarnings({"unused", "WeakerAccess"})
public class Outcome {
    private final Type type;
    private final Start start;
    private final OnlineResponseDataRestartCondition onlineResponseDataRestartCondition;
    private final Optional<List<Tlv>> onlineResponseData;
    private final Cvm cvm;
    private final Optional<UserInterfaceRequest> uiRequestOnOutcome;
    private final Optional<UserInterfaceRequest> uiRequestOnRestart;
    private final List<Tlv> discretionaryData;
    private final AlternateInterfacePreference alternateInterfacePreference;
    private final ReceiptPreference receiptPreference;
    private final int fieldOffRequest;
    private final int removalTimeout;
    private final List<Tlv> dataRecord;
    private final List<Tlv> tlvDb;


    public Outcome(final Type type,
                   final Start start,
                   final OnlineResponseDataRestartCondition onlineResponseDataRestartCondition,
                   Optional<List<Tlv>> onlineResponseData,
                   Cvm cvm,
                   final Optional<UserInterfaceRequest> uiRequestOnOutcome,
                   final Optional<UserInterfaceRequest> uiRequestOnRestart,
                   final List<Tlv> dataRecord,
                   final List<Tlv> discretionaryData,
                   final AlternateInterfacePreference alternateInterfacePreference,
                   final ReceiptPreference receiptPreference,
                   final int fieldOffRequest,
                   final int removalTimeout,
                   final List<Tlv> tlvDb) {

        this.type = type;
        this.start = start;
        this.onlineResponseDataRestartCondition = onlineResponseDataRestartCondition;
        this.onlineResponseData = onlineResponseData;
        this.cvm = cvm;
        this.uiRequestOnOutcome = uiRequestOnOutcome;
        this.uiRequestOnRestart = uiRequestOnRestart;
        this.dataRecord = dataRecord;
        this.discretionaryData = discretionaryData;
        this.alternateInterfacePreference = alternateInterfacePreference;
        this.receiptPreference = receiptPreference;
        this.fieldOffRequest = fieldOffRequest;
        this.removalTimeout = removalTimeout;
        this.tlvDb = tlvDb;
    }


    public Type getType() {
        return type;
    }


    public Start getStart() {
        return start;
    }


    public OnlineResponseDataRestartCondition getOnlineResponseDataRestartCondition() {
        return onlineResponseDataRestartCondition;
    }


    public Optional<List<Tlv>> getOnlineResponseData() {
        return onlineResponseData;
    }


    public Cvm getCvm() {
        return cvm;
    }


    public boolean isTlvDbPresent() {
        return dataRecord != null;
    }


    public AlternateInterfacePreference getAlternateInterfacePreference() {
        return alternateInterfacePreference;
    }


    public ReceiptPreference getReceiptPreference() {
        return receiptPreference;
    }


    public int getFieldOffRequest() {
        return fieldOffRequest;
    }


    public int getRemovalTimeout() {
        return removalTimeout;
    }


    public Optional<UserInterfaceRequest> getUiRequestOnOutcome() {
        return uiRequestOnOutcome != null ? uiRequestOnOutcome : Optional.empty();
    }


    public Optional<UserInterfaceRequest> getUiRequestOnRestart() {
        return uiRequestOnRestart != null ? uiRequestOnRestart : Optional.empty();
    }


    public static Outcome createTryAnotherCardOutcome(List<Tlv> discretionaryData) {
        Builder b = new Builder(Type.END_APPLICATION);

        UserInterfaceRequest uiReq = new UserInterfaceRequest(StandardMessages.TRY_ANOTHER_CARD,
                ContactlessTransactionStatus.NOT_READY,
                13,
                null,
                null,
                0,
                null);
        b.uiRequestOnOutcome(uiReq);
        b.removalTimeout(0);
        b.discretionaryData(discretionaryData);

        return b.build();
    }


    public static Outcome createTryAgainOutcome(List<Tlv> discretionaryData) {
        Builder b = new Builder(Type.TRY_AGAIN);

        b.removalTimeout(0);
        b.start(Start.B);
        b.discretionaryData(discretionaryData);

        return b.build();
    }


    public static Outcome createTryAnotherInterface(List<Tlv> discretionaryData) {
        Builder b = new Builder(Type.TRY_ANOTHER_INTERFACE);
        UserInterfaceRequest ur = new UserInterfaceRequest(StandardMessages.INSERT_OR_SWIPE_CARD,
                ContactlessTransactionStatus.PROCESSING_ERROR,
                0,
                null,
                null,
                0,
                null
        );
        b.uiRequestOnOutcome(ur);
        b.discretionaryData(discretionaryData);
        return b.build();
    }


    public enum Type {
        SELECT_NEXT,
        TRY_AGAIN,
        APPROVED,
        DECLINED,
        ONLINE_REQUEST,
        TRY_ANOTHER_INTERFACE,
        END_APPLICATION
    }


    public enum Start {
        B, C, D, NOT_APPLICABLE
    }


    public enum OnlineResponseDataRestartCondition {
        EMV_DATA,
        ANY,
        NOT_APPLICABLE
    }


    public enum Cvm {
        ONLINE_PIN,
        CONFIRMATION_CODE_VERIFIED,
        OBTAIN_SIGNATURE,
        NO_CVM,
        NOT_APPLICABLE
    }


    public enum AlternateInterfacePreference {
        CONTACT_CHIP,
        MAG_STRIPE,
        NOT_APPLICABLE
    }


    public enum ReceiptPreference {
        YES,
        DO_NOT_CARE,
        NOT_APPLICABLE
    }


    public List<Tlv> getDataRecord() {
        return dataRecord;
    }


    public boolean isDiscretionaryDataPresent() {
        return discretionaryData != null && discretionaryData.size() > 0;
    }


    public boolean isDataRecordPresent() {
        return dataRecord != null && dataRecord.size() > 0;
    }


    public List<Tlv> getDiscretionaryData() {
        return discretionaryData;
    }


    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    @Override
    public String toString() {
        return "Outcome Type: " + type + " " +
                "Start: " + start + " " +
                "CVM: " + cvm + " " +
                "UI Request On Outcome: " + (uiRequestOnOutcome.isPresent() ? uiRequestOnOutcome.get().getMessage() : "") + " " +
                "Discretionary Data Present: " + isDiscretionaryDataPresent() + " " +
                "Data Record Present: " + isDataRecordPresent() + " " +
                "Field Off Request: " + fieldOffRequest + " ";
    }


    public List<Tlv> getTlvDb() {
        return tlvDb;
    }


    @SuppressWarnings("UnusedReturnValue")
    public static class Builder {
        private final Type type;

        private Start start = Start.NOT_APPLICABLE;
        private OnlineResponseDataRestartCondition onlineResponseDataRestartCondition =
                OnlineResponseDataRestartCondition.NOT_APPLICABLE;

        private Optional<List<Tlv>> onlineResponseData = Optional.empty();
        private Cvm cvm = Cvm.NOT_APPLICABLE;
        private Optional<UserInterfaceRequest> uiRequestOnOutcome = Optional.empty();
        private Optional<UserInterfaceRequest> uiRequestOnRestart = Optional.empty();
        private List<Tlv> dataRecord;
        private List<Tlv> discretionaryData;
        private List<Tlv> tlvDb;
        private AlternateInterfacePreference alternateInterfacePreference =
                AlternateInterfacePreference.NOT_APPLICABLE;
        private ReceiptPreference receiptPreference = ReceiptPreference.NOT_APPLICABLE;
        private int fieldOffRequest = 0;
        private int removalTimeout = 0;


        public Builder(final Type type) {
            this.type = type;
        }


        public Outcome build() {
            return new Outcome(type,
                    start,
                    onlineResponseDataRestartCondition,
                    onlineResponseData,
                    cvm,
                    uiRequestOnOutcome,
                    uiRequestOnRestart,
                    dataRecord,
                    discretionaryData,
                    alternateInterfacePreference,
                    receiptPreference,
                    fieldOffRequest,
                    removalTimeout, tlvDb);
        }


        public Builder start(final Outcome.Start start) {
            this.start = start;
            //noinspection unchecked
            return this;
        }


        public Builder onlineResponseDataRestartCondition(final OnlineResponseDataRestartCondition
                                                                  onlineResponseDataRestartCondition) {

            this.onlineResponseDataRestartCondition = onlineResponseDataRestartCondition;
            return this;
        }


        public Builder onlineResponseData(final Optional<List<Tlv>> onlineResponseData) {
            this.onlineResponseData = onlineResponseData;
            return this;
        }


        public Builder cvm(Cvm cvm) {
            this.cvm = cvm;

            return this;
        }


        public Builder uiRequestOnOutcome(final UserInterfaceRequest uiRequestOnOutcome) {
            this.uiRequestOnOutcome = Optional.of(uiRequestOnOutcome);
            return this;
        }


        public Builder uiRequestOnRestart(UserInterfaceRequest uiRequestOnRestart) {
            this.uiRequestOnRestart = Optional.of(uiRequestOnRestart);
            return this;
        }


        public Builder dataRecord(final List<Tlv> dataRecord) {
            this.dataRecord = dataRecord;

            return this;
        }


        public Builder discretionaryData(final List<Tlv> discretionaryData) {
            this.discretionaryData = discretionaryData;

            return this;
        }


        public Builder alternateInterfacePreference(final Outcome.AlternateInterfacePreference
                                                            alternateInterfacePreference) {

            this.alternateInterfacePreference = alternateInterfacePreference;

            return this;
        }


        public Builder receiptPreference(final Outcome.ReceiptPreference receiptPreference) {
            this.receiptPreference = receiptPreference;

            return this;
        }


        public Builder fieldOffRequest(final int fieldOffRequest) {
            this.fieldOffRequest = fieldOffRequest;

            return this;
        }


        public Builder removalTimeout(final int removalTimeout) {
            this.removalTimeout = removalTimeout;

            return this;
        }


        public Builder tlvDb(final List<Tlv> tlvDb) {
            this.tlvDb = tlvDb;

            return this;
        }
    }
}
