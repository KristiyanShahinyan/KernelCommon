package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import java8.util.Optional;


public class TlvMapReadOnlyImpl implements TlvMapReadOnly {
    private final TlvMap map;


    public TlvMapReadOnlyImpl(TlvMap map) {
        this.map = map;
    }


    @Override
    public Tlv get(EmvTag tag) {
        return map.get(tag);
    }


    @Override
    public Optional<Tlv> getAsOptional(EmvTag tag) {
        return map.getAsOptional(tag);
    }


    @Override
    public boolean isTagPresentAndNonEmpty(EmvTag tag) {
        return map.isTagPresentAndNonEmpty(tag);
    }


    @Override
    public boolean isTagPresent(EmvTag tag) {
        return map.isTagPresent(tag);
    }


    @Override
    public List<Tlv> asList() {
        return map.asList();
    }
}
