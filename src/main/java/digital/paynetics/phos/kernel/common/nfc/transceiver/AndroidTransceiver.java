package digital.paynetics.phos.kernel.common.nfc.transceiver;


import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import digital.paynetics.phos.kernel.common.misc.TimeProvider;
import digital.paynetics.phos.kernel.common.nfc.ApduCommandPackage;
import digital.paynetics.phos.kernel.common.nfc.ApduResponsePackage;
import digital.paynetics.phos.kernel.common.nfc.iso_dep.IsoDepWrapper;



public class AndroidTransceiver implements Transceiver {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IsoDepWrapper isoDep;
    private final TimeProvider timeProvider;
    private final int minDelayBetweenCommands;
    private volatile long lastCommandTs;

    private final List<StatItem> stats = new ArrayList<>();



    public AndroidTransceiver(final IsoDepWrapper isoDep, TimeProvider timeProvider, final int minDelayBetweenCommands) {
        if (!isoDep.isConnected()) {
            throw new IllegalStateException("isoDep not connected");
        }
        this.timeProvider = timeProvider;
        this.isoDep = isoDep;
        this.minDelayBetweenCommands = minDelayBetweenCommands;
    }


    @Override
    public ApduResponsePackage transceive(final ApduCommandPackage pck) throws IOException {
        logger.debug("Sending: {}", ByteUtils.toHexString(pck.toBytes(), true));

        if (isoDep == null) {
            throw new IllegalStateException("Not initialized.");
        }

        if (minDelayBetweenCommands > 0) {
            long toWait = lastCommandTs + minDelayBetweenCommands - timeProvider.getVmTime();
            if (toWait > 0) {
                try {
                    Thread.sleep(toWait);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
        }

        lastCommandTs = timeProvider.getVmTime();
        long start = timeProvider.getVmTime();
        boolean isSuccess = false;
        long took = 0;
        try {
            final byte[] data = isoDep.transceive(pck.toBytes());
            took = timeProvider.getVmTime() - start;
            isSuccess = true;
            logger.debug("Received ({}): {}", took, ByteUtils.toHexString(data, true));
            try {
                return new ApduResponsePackage(data);
            } catch (Exception e) {
                logger.warn("Cannot create ApduResponsePackage - unknown status word");
                throw new IOException(e);
            }
        } finally {
            stats.add(new StatItem(pck.getCla(), pck.getIns(), (int) took, isSuccess));
        }
    }


    @Override
    public List<StatItem> getStats() {
        return stats;
    }


    @Override
    public void close() {
        try {
            isoDep.close();
        } catch (IOException e) {
            logger.error("Cannot close IsoDep: {}", e);
        }
    }
}
