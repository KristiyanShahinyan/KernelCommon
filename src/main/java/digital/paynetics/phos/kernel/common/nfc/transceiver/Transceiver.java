package digital.paynetics.phos.kernel.common.nfc.transceiver;


import java.io.IOException;
import java.util.List;

import digital.paynetics.phos.kernel.common.nfc.ApduCommandPackage;
import digital.paynetics.phos.kernel.common.nfc.ApduResponsePackage;



public interface Transceiver {
    ApduResponsePackage transceive(ApduCommandPackage pck) throws IOException;

    List<StatItem> getStats();

    void close();


    class StatItem {
        private final byte[] cmd = new byte[2];
        private final int took;
        private final boolean isSuccess;


        public StatItem(byte cla, byte ins, int took, boolean isSuccess) {
            cmd[0] = cla;
            cmd[1] = ins;
            this.took = took;
            this.isSuccess = isSuccess;
        }


        public byte[] getCmd() {
            return cmd.clone();
        }


        public int getTook() {
            return took;
        }


        public boolean isSuccess() {
            return isSuccess;
        }
    }
}
