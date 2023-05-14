package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import javax.inject.Inject;

import digital.paynetics.phos.exceptions.EmvException;


public class AflsExtractorImpl implements AflsExtractor {
    @Inject
    public AflsExtractorImpl() {
    }


    @Override
    public List<Afl> extractAfls(byte[] aflRaw) throws EmvException {
        return KernelUtils.extractAfls(aflRaw);
    }
}
