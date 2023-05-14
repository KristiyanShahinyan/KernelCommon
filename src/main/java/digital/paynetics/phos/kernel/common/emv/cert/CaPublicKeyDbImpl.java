package digital.paynetics.phos.kernel.common.emv.cert;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import java8.util.Optional;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


/**
 * Contains data for public keys for only one RID
 */
public class CaPublicKeyDbImpl implements CaPublicKeyDb {
    private final Map<Integer, CaPublicKeyData> map = new HashMap<>();
    private final String rid;


    @Inject
    public CaPublicKeyDbImpl(String rid) {
        this.rid = rid;
    }


    @Override
    public String getRid() {
        return rid;
    }


    @Override
    public void add(CaPublicKeyData caPublicKeyData) {
        if (exists(caPublicKeyData.getIndex())) {
            throw new IllegalArgumentException(format("Index {} already added.",
                    caPublicKeyData.getIndex()));
        }

        map.put(caPublicKeyData.getIndex(), caPublicKeyData);
    }


    @Override
    public Optional<CaPublicKeyData> getByIndex(int index) {
        return Optional.ofNullable(map.get(index));
    }


    @Override
    public boolean exists(int index) {
        return map.containsKey(index);
    }
}
