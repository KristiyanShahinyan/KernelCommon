package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import digital.paynetics.phos.kernel.common.emv.Outcome;


public class IntermediateOutcomeStoreImpl implements IntermediateOutcomeStore {
    private final List<Outcome> outcomes = new ArrayList<>();


    @Inject
    public IntermediateOutcomeStoreImpl() {
    }


    @Override
    public void add(Outcome oc) {
        outcomes.add(oc);
    }


    @Override
    public List<Outcome> get() {
        return new ArrayList<>(outcomes);
    }


    @Override
    public void clear() {
        outcomes.clear();
    }
}
