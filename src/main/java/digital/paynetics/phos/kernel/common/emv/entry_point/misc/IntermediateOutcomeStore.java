package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.Outcome;


public interface IntermediateOutcomeStore {
    void add(Outcome oc);

    List<Outcome> get();

    void clear();
}
