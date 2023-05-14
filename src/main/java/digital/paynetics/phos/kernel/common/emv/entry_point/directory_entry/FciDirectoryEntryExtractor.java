package digital.paynetics.phos.kernel.common.emv.entry_point.directory_entry;

import java.util.List;

import digital.paynetics.phos.exceptions.TlvException;


public interface FciDirectoryEntryExtractor {
    List<FciDirectoryEntry> extractCardApplications(byte[] data) throws TlvException;
}
