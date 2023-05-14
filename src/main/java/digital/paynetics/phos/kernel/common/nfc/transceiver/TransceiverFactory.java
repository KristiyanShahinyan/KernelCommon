package digital.paynetics.phos.kernel.common.nfc.transceiver;

import digital.paynetics.phos.kernel.common.nfc.iso_dep.IsoDepWrapper;


public interface TransceiverFactory {
    Transceiver create(IsoDepWrapper isoDep, int minDellayBetweenCommands);
}
