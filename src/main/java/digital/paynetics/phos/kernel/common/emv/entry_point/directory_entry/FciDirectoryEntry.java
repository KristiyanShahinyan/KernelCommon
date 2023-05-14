package digital.paynetics.phos.kernel.common.emv.entry_point.directory_entry;

import java.util.Comparator;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


public class FciDirectoryEntry {
    private static final int PRIORITY_LAST = 16;

    private final byte[] adfName;
    private final String label;
    private final int priority;
    private final byte[] kernelIdentifier;
    private final byte[] extendedSelection;


    public FciDirectoryEntry(byte[] adfName, String label, int priority, byte[] kernelIdentifier, byte[] extendedSelection) {
        this.adfName = adfName;
        this.label = label;
        this.extendedSelection = extendedSelection;

        // According to:Book B Entry Point Specification, 3.3 Combination Selection
        // "The priority of the Combination is indicated by means of an Application Priority
        // Indicator with a value of '1' as the highest priority and 'F' the lowest. A value of '0'
        // means no priority is assigned and has the same priority as 'F'."
        if (priority == 0) {
            this.priority = PRIORITY_LAST;
        } else {
            this.priority = priority;
        }
        this.kernelIdentifier = kernelIdentifier;
    }


    public FciDirectoryEntry(byte[] adfName, String label, int priority) {
        this.adfName = adfName;
        this.label = label;
        this.priority = priority;
        this.kernelIdentifier = null;
        this.extendedSelection = null;
    }


    public FciDirectoryEntry(byte[] adfName, String label) {
        this.adfName = adfName;
        this.label = label;
        this.priority = PRIORITY_LAST;
        this.kernelIdentifier = null;
        this.extendedSelection = null;
    }


    public static int getPriorityLast() {
        return PRIORITY_LAST;
    }


    public byte[] getAdfName() {
        return adfName.clone();
    }


    public boolean isAdfNamePresent() {
        return adfName != null && adfName.length > 0;
    }


    public String getAdfNameAsString() {
        return ByteUtils.toHexString(adfName);
    }


    public String getLabel() {
        return label;
    }


    public int getPriority() {
        return priority;
    }


    public boolean isKernelIdentifierPresent() {
        return kernelIdentifier != null && kernelIdentifier.length > 0;
    }


    public byte[] getKernelIdentifier() {
        if (kernelIdentifier != null) {
            return kernelIdentifier.clone();
        } else {
            return null;
        }
    }


    public byte[] getExtendedSelection() {
        if (extendedSelection != null) {
            return extendedSelection.clone();
        } else {
            return null;
        }
    }


    public boolean isExtendedSelectionPresent() {
        return extendedSelection != null && extendedSelection.length > 0;
    }


    public String getExtendedSelectionAsString() {
        return ByteUtils.toHexString(adfName);
    }


    @Override
    public String toString() {
        return format("App ID: {}, Label: {}, Priority: {}, Kernel: {}", ByteUtils.toHexString(adfName), label,
                priority, kernelIdentifier != null ? ByteUtils.toHexString(kernelIdentifier) : null);
    }


    public static Comparator<FciDirectoryEntry> sortComparator() {
        return new Comparator<FciDirectoryEntry>() {
            @Override
            public int compare(FciDirectoryEntry o1, FciDirectoryEntry o2) {
                return o1.priority - o2.priority;
            }
        };
    }

}
