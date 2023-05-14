package digital.paynetics.phos.kernel.common.nfc;

import digital.paynetics.phos.kernel.common.nfc.transceiver.Transceiver;


public interface NfcManager {

    void init(Listener listener);

    void startPolling();

    void stopPolling();

    void exit();

    interface Listener {
        void onNfcTag(Transceiver transceiver);
    }
}
