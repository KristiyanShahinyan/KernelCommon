package digital.paynetics.phos.kernel.common.emv.entry_point.selection;

import digital.paynetics.phos.kernel.common.emv.kernel.common.KernelType;
import digital.paynetics.phos.kernel.common.misc.PreprocessedApplication;


public final class SelectionCandidate implements Comparable<SelectionCandidate> {
    private final byte[] adfName;
    private final PreprocessedApplication preprocessedApplication;
    private final KernelType finalKernelType;

    private final int priority;

    private final byte[] extendedSelection;


    public SelectionCandidate(byte[] adfName,
                              PreprocessedApplication preprocessedApplication,
                              KernelType finalKernelType,
                              int priority,
                              byte[] extendedSelection) {


        this.adfName = adfName;
        this.preprocessedApplication = preprocessedApplication;
        this.finalKernelType = finalKernelType;
        this.priority = priority;
        this.extendedSelection = extendedSelection;
    }


    public byte[] getAdfName() {
        return adfName.clone();
    }


    public PreprocessedApplication getPreprocessedApplication() {
        return preprocessedApplication;
    }


    public KernelType getFinalKernelType() {
        return finalKernelType;
    }


    public int getPriority() {
        return priority;
    }


    public byte[] getExtendedSelection() {
        if (extendedSelection != null) {
            return extendedSelection.clone();
        } else {
            return null;
        }
    }


    @Override
    public int compareTo(SelectionCandidate candidate) {
        return getPriority() - candidate.getPriority();
    }
}
