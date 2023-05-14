package digital.paynetics.phos.kernel.common.nfc.iso_dep;

import java.io.IOException;


public interface IsoDepWrapper {
    void connect() throws IOException;

    void close() throws IOException;

    byte[] transceive(byte[] data) throws IOException;

    boolean isConnected();
}
