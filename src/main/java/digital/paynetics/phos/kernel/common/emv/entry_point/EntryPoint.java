package digital.paynetics.phos.kernel.common.emv.entry_point;

import java.util.List;

import digital.paynetics.phos.kernel.common.crypto.EncDec;
import digital.paynetics.phos.kernel.common.emv.Outcome;
import digital.paynetics.phos.kernel.common.emv.ui.EntryPointUiRequester;
import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import digital.paynetics.phos.kernel.common.misc.CardAppConfiguration;
import digital.paynetics.phos.kernel.common.misc.CardApplication;
import digital.paynetics.phos.kernel.common.misc.CertificateData;
import digital.paynetics.phos.kernel.common.misc.Currency;
import digital.paynetics.phos.kernel.common.misc.TerminalConfig;
import digital.paynetics.phos.kernel.common.misc.TransactionType;
import digital.paynetics.phos.kernel.common.nfc.NfcManager;
import digital.paynetics.phos.kernel.common.nfc.iso_dep.IsoDepWrapper;
import digital.paynetics.phos.kernel.common.nfc.transceiver.Transceiver;
import java8.util.Optional;


public interface EntryPoint {
    void init(Listener listener,
              EntryPointUiRequester entryPointUiRequester,
              NfcManager nfcManager,
              TerminalConfig terminalConfig,
              CertificateData certificateData,
              EncDec encDec);

    Optional<Outcome> startA_preProcessing(
            List<CardApplication> apps,
            List<CardAppConfiguration> appConfs,
            int amountAuthorized,
            int amountOther,
            Currency currency,
            TransactionType transactionType);

    /**
     * This method might be used in the future when the app is supporting environments with a fixed amount (e.g. a
     * vending machine with identically priced goods)
     *
     * @param isoDepWrapper IsoDep wrapper
     */
    Outcome startB_protocolActivationFromReaderDirect(IsoDepWrapper isoDepWrapper);

    void stopSignal();

    interface Listener {
        void onStartedReadingCard();

        void onPreProcessingEnded();

        void onStartedPolling();

        void onOutcome(Outcome oc, List<Outcome> intermediateOutcomes, Optional<UserInterfaceRequest> uiReq);

        void onEndedReadingCard();
    }

    List<Transceiver.StatItem> getTransceiverStats();
}
