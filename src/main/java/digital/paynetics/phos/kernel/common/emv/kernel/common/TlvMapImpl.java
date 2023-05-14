package digital.paynetics.phos.kernel.common.emv.kernel.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import digital.paynetics.phos.kernel.common.emv.tag.EmvTag;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import java8.util.Optional;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


public class TlvMapImpl implements TlvMap {
    private final Map<EmvTag, Tlv> map = new HashMap<>();


    @Inject
    public TlvMapImpl() {
    }


    public TlvMapImpl(List<Tlv> tlvs) {
        addAll(tlvs);
    }



    @Override
    public void add(Tlv tlv) {
        Tlv old = map.get(tlv.getTag());
        if (old != null) {
            throw new IllegalStateException(format("Tag {} already has value. Old {}, new {}", tlv.getTag(),
                    ByteUtils.toHexString(old.getValueBytes()), ByteUtils.toHexString(tlv.getValueBytes())));
        }

        map.put(tlv.getTag(), tlv);
    }


    @Override
    public void add(Optional<Tlv> tlvO) {
        if (tlvO.isPresent()) {
            add(tlvO.get());
        }
    }


    @Override
    public void update(Tlv tlv) {
        Tlv old = map.get(tlv.getTag());
        if (old == null) {
            throw new IllegalArgumentException(format("Tag {} is missing.", tlv.getTag()));
        }

        map.put(tlv.getTag(), tlv);
    }


    @Override
    public void updateOrAdd(Tlv tlv) {
        map.put(tlv.getTag(), tlv);
    }


    @Override
    public void addAll(List<Tlv> tlvs) {
        for (Tlv tlv : tlvs) {
            add(tlv);
        }
    }


    @Override
    public void addAllNonExistingTags(List<Tlv> tlvs) {
        for (Tlv tlv : tlvs) {
            if (!isTagPresent(tlv.getTag())) {
                add(tlv);
            }
        }
    }


    @Override
    public void remove(EmvTag emvTag) {
        for(Iterator<Map.Entry<EmvTag, Tlv>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<EmvTag, Tlv> entry = it.next();
            if(entry.getKey() == emvTag) {
                it.remove();
                break;
            }
        }
    }


    @Override
    public Tlv get(EmvTag tag) {
        Tlv ret = map.get(tag);

        if (ret == null) {
            throw new IllegalStateException(format("No such tag found: {}", tag));
        }

        return ret;
    }


    @Override
    public Optional<Tlv> getAsOptional(EmvTag tag) {
        return Optional.ofNullable(map.get(tag));
    }


    @Override
    public boolean isTagPresentAndNonEmpty(EmvTag tag) {
        Tlv tlv = map.get(tag);
        return tlv != null && tlv.getValueBytes().length > 0;
    }


    @Override
    public boolean isTagPresent(EmvTag tag) {
        Tlv tlv = map.get(tag);
        return tlv != null;
    }


    @Override
    public List<Tlv> asList() {
        return Collections.unmodifiableList(new ArrayList<>(map.values()));
    }
}
