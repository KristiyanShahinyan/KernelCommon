package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import digital.paynetics.phos.kernel.common.emv.entry_point.misc.TransactionData;
import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import digital.paynetics.phos.kernel.common.misc.CountryCode;
import digital.paynetics.phos.kernel.common.misc.RandomGenerator;
import digital.paynetics.phos.kernel.common.misc.TerminalType;
import digital.paynetics.phos.kernel.common.misc.TransactionTimestamp;

import static digital.paynetics.phos.kernel.common.misc.ByteUtils.leftPad;


public class CommonDolDataPreparerImpl implements CommonDolDataPreparer {
    private final RandomGenerator random;


    @Inject
    public CommonDolDataPreparerImpl(@Named("RandomGenerator for CommonDolDataPreparerImpl")
                                             RandomGenerator random) {
        this.random = random;
    }


    @Override
    public TlvMap prepare(CountryCode countryCode,
                          TerminalType terminalType,
                          TransactionData transactionData,
                          TransactionTimestamp ts,
                          List<Tlv> tlvConfigData) {

        TlvMap ret = new TlvMapImpl();
        if (tlvConfigData != null) {
            ret.addAll(tlvConfigData);
        }

        byte[] amountAuthorizedB = leftPad(ByteUtils.intToUnpackedBcd(transactionData.getAmountAuthorized()), 6);
        Tlv amountAuthorized = new Tlv(EmvTag.AMOUNT_AUTHORISED_NUMERIC,
                amountAuthorizedB.length,
                amountAuthorizedB);
        ret.add(amountAuthorized);

        byte[] amountOtherB = leftPad(ByteUtils.intToUnpackedBcd(transactionData.getAmountOther()), 6);
        Tlv amountOther = new Tlv(EmvTag.AMOUNT_OTHER_NUMERIC,
                amountOtherB.length,
                amountOtherB);
        ret.add(amountOther);

        if (!ret.isTagPresentAndNonEmpty(EmvTag.TERMINAL_COUNTRY_CODE)) {
            byte[] terminalCountryCoreB = ByteUtils.intToUnpackedBcd(countryCode.getNumeric());
            Tlv terminalCountryCode = new Tlv(EmvTag.TERMINAL_COUNTRY_CODE,
                    terminalCountryCoreB.length,
                    terminalCountryCoreB);
            ret.add(terminalCountryCode);
        }

        byte[] transactionCurrencyB = ByteUtils.intToUnpackedBcd(transactionData.getCurrency().getISOCodeNumeric());
        Tlv transactionCurrency = new Tlv(EmvTag.TRANSACTION_CURRENCY_CODE,
                transactionCurrencyB.length,
                transactionCurrencyB);
        ret.add(transactionCurrency);

        byte[] transactionDateB = ts.getDateBytes();
        Tlv transactionDate = new Tlv(EmvTag.TRANSACTION_DATE,
                transactionDateB.length,
                transactionDateB);
        ret.add(transactionDate);

        byte[] transactionTimeB = ts.getTimeBytes();
        Tlv transactionTime = new Tlv(EmvTag.TRANSACTION_TIME,
                transactionTimeB.length,
                transactionTimeB);
        ret.add(transactionTime);

        byte[] transactionTypeB = new byte[]{transactionData.getType().getCode()};
        Tlv transactionType = new Tlv(EmvTag.TRANSACTION_TYPE,
                1,
                transactionTypeB);
        ret.updateOrAdd(transactionType);

        byte[] randomB = new byte[4];
        random.nextBytes(randomB);
        Tlv random = new Tlv(EmvTag.UNPREDICTABLE_NUMBER, 4, randomB);
        ret.add(random);

        if (!ret.isTagPresentAndNonEmpty(EmvTag.TERMINAL_TYPE)) {
            // Mastercard tests require terminal_type to be set with different values for different transaction types (which of course
            // is retarded) so we might get the terminal type in customTlvConfigData and we don't want to override it
            byte[] terminalTypeB = new byte[]{terminalType.getCode()};
            Tlv terminalTypeTlv = new Tlv(EmvTag.TERMINAL_TYPE, terminalTypeB.length, terminalTypeB);
            ret.add(terminalTypeTlv);
        }


        return ret;
    }
}
