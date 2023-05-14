package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.entry_point.misc.TransactionData;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.misc.CountryCode;
import digital.paynetics.phos.kernel.common.misc.TerminalType;
import digital.paynetics.phos.kernel.common.misc.TransactionTimestamp;


public interface CommonDolDataPreparer {
    TlvMap prepare(CountryCode countryCode,
                   TerminalType terminalType,
                   TransactionData transactionData,
                   TransactionTimestamp ts,
                   List<Tlv> tlvConfigData);
}
