package digital.paynetics.phos.kernel.common.emv.ui;


import digital.paynetics.phos.kernel.common.misc.Currency;
import digital.paynetics.phos.kernel.common.misc.PhosMessageFormat;
import java8.util.Optional;


public class UserInterfaceRequest {
    private final StandardMessages message;
    private final ContactlessTransactionStatus status;
    private final int holdTime;
    private final Optional<String> languagePreference;
    private final Optional<ValueQualifier> valueQualifier;
    private final int value;
    private final Optional<Currency> currency;


    public UserInterfaceRequest(StandardMessages message,
                                ContactlessTransactionStatus status,
                                int holdTime,
                                String languagePreference,
                                ValueQualifier valueQualifier,
                                int value,
                                Currency currency) {

        this.message = message;
        this.status = status;
        this.holdTime = holdTime;
        this.languagePreference = Optional.ofNullable(languagePreference);
        this.valueQualifier = Optional.ofNullable(valueQualifier);
        this.value = value;
        this.currency = Optional.ofNullable(currency);
    }


    public StandardMessages getMessage() {
        return message;
    }


    public ContactlessTransactionStatus getStatus() {
        return status;
    }


    public int getHoldTime() {
        return holdTime;
    }


    public Optional<String> getLanguagePreference() {
        return languagePreference;
    }


    public Optional<ValueQualifier> getValueQualifier() {
        return valueQualifier;
    }


    public int getValue() {
        return value;
    }


    public Optional<Currency> getCurrency() {
        return currency;
    }


    public enum ValueQualifier {
        AMOUNT,
        BALANCE
    }


    @Override
    public String toString() {
        return PhosMessageFormat.format("[UI Req] message: {}, state: {}, hold time: {}, lang pref: {}, value qualifier: {}, " +
                        "value: {}, currency: {}", message, status, holdTime,
                languagePreference.isPresent() ? languagePreference.get() : "n/a",
                valueQualifier.isPresent() ? valueQualifier.get() : "n/a", value, currency.isPresent() ? currency.get() : "n/a");
    }
}
