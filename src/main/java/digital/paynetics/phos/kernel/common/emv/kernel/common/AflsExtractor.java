package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import digital.paynetics.phos.exceptions.EmvException;


public interface AflsExtractor {
    List<Afl> extractAfls(byte[] aflRaw) throws EmvException;
}
