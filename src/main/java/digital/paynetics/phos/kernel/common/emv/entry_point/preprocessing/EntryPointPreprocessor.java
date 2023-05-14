package digital.paynetics.phos.kernel.common.emv.entry_point.preprocessing;

import java.util.List;

import digital.paynetics.phos.kernel.common.misc.CardAppConfiguration;
import digital.paynetics.phos.kernel.common.misc.CardApplication;
import digital.paynetics.phos.kernel.common.misc.Currency;
import digital.paynetics.phos.kernel.common.misc.PreprocessedApplication;
import digital.paynetics.phos.kernel.common.misc.TransactionType;
import java8.lang.FunctionalInterface;


/**
 * 'Start A' as described in EMV book A & B
 * Pre-processing as described in Book B (Version 2.6), 3.1.1 Pre-Processing Requirements
 */
@FunctionalInterface
public interface EntryPointPreprocessor {
    List<PreprocessedApplication> preProcess(
            List<CardApplication> apps,
            List<CardAppConfiguration> appConfs,
            int amountAuthorized,
            int amountOther,
            Currency currency,
            TransactionType transactionType);


}
