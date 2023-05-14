package digital.paynetics.phos.kernel.common.emv.kernel.common;

import digital.paynetics.phos.kernel.common.emv.Outcome;


public final class CvmSelectionResult {
    private final Outcome.Cvm cvm;
    private final CvmResults results;


    public CvmSelectionResult(Outcome.Cvm cvm, CvmResults results) {
        this.cvm = cvm;
        this.results = results;
    }


    public Outcome.Cvm getCvm() {
        return cvm;
    }


    public CvmResults getResults() {
        return results;
    }
}
