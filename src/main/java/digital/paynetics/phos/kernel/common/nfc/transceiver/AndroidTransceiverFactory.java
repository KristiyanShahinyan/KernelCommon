package digital.paynetics.phos.kernel.common.nfc.transceiver;

import javax.inject.Inject;

import digital.paynetics.phos.kernel.common.misc.TimeProvider;
import digital.paynetics.phos.kernel.common.nfc.iso_dep.IsoDepWrapper;


public class AndroidTransceiverFactory implements TransceiverFactory {
    private final TimeProvider timeProvider;

    @Inject
    public AndroidTransceiverFactory(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }


    @Override
    public Transceiver create(final IsoDepWrapper isoDep, int minDelayBetweenCommands) {
        return new AndroidTransceiver(isoDep, timeProvider, minDelayBetweenCommands);
    }
}
