package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import java8.util.Optional;


public interface TlvMapReadOnly {
    Tlv get(EmvTag tag);

    Optional<Tlv> getAsOptional(EmvTag tag);

    boolean isTagPresentAndNonEmpty(EmvTag tag);

    boolean isTagPresent(EmvTag tag);

    List<Tlv> asList();
}
