package digital.paynetics.phos.kernel.common.emv.entry_point.selection;

import java.io.IOException;
import java.util.List;

import digital.paynetics.phos.kernel.common.emv.Outcome;
import digital.paynetics.phos.exceptions.EmvException;
import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.misc.PreprocessedApplication;
import digital.paynetics.phos.kernel.common.nfc.transceiver.Transceiver;
import java8.util.Optional;


public interface ApplicationSelector {
    Optional<Outcome> init(List<PreprocessedApplication> appsPreprocessed, Transceiver transceiver)
            throws IOException, TlvException, EmvException;

    Optional<SelectedApplication> select(Transceiver transceiver) throws IOException;
}
