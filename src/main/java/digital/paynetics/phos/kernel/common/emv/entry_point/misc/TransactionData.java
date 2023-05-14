package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import digital.paynetics.phos.kernel.common.misc.Currency;
import digital.paynetics.phos.kernel.common.misc.TransactionType;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


public final class TransactionData {
    private final int amountAuthorized;
    private final int amountOther;
    private final Currency currency;
    private final TransactionType type;


    public TransactionData(final int amountAuthorized,
                           final int amountOther,
                           final Currency currency,
                           final TransactionType type) {

        if (amountAuthorized < 0) {
            throw new IllegalArgumentException("Invalid Amount, Authorized: " + amountAuthorized);
        }

        if (amountOther < 0) {
            throw new IllegalArgumentException("Invalid Amount, Other: " + amountOther);
        }

        this.amountAuthorized = amountAuthorized;
        this.amountOther = amountOther;
        this.currency = currency;
        this.type = type;
    }


    public int getAmountAuthorized() {
        return amountAuthorized;
    }


    public int getAmountOther() {
        return amountOther;
    }


    public Currency getCurrency() {
        return currency;
    }


    public TransactionType getType() {
        return type;
    }


    @Override
    public String toString() {
        return format("[Transaction data] type: {} amount: {}, amount other: {}, Currency: {}",
                type, amountAuthorized, amountOther, currency
        );
    }
}
