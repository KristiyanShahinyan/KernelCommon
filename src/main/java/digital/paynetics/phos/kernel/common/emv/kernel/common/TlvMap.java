package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import java8.util.Optional;


public interface TlvMap extends TlvMapReadOnly {
    void add(Tlv tlv);

    void add(Optional<Tlv> tlv);

    void update(Tlv tlv);

    void updateOrAdd(Tlv tlv);

    void addAll(List<Tlv> tlvs);

    void addAllNonExistingTags(List<Tlv> tlvs);

    void remove(EmvTag emvTag);
}
