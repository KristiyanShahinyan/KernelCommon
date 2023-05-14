package digital.paynetics.phos.kernel.common.emv.cert;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import java8.util.Optional;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


public class CaRidDbImpl implements CaRidDb {
    private final Map<String, CaPublicKeyDb> map = new HashMap<>();


    @Inject
    public CaRidDbImpl() {
    }


    @Override
    public void addDb(CaPublicKeyDb db) {
        if (exists(db.getRid())) {
            throw new IllegalArgumentException(format("RID {} already added", db.getRid()));
        }

        map.put(db.getRid(), db);
    }


    @Override
    public boolean exists(String rid) {
        return map.containsKey(rid);
    }


    @Override
    public void clear() {
        map.clear();
    }


    @Override
    public Optional<CaPublicKeyDb> getByRid(String rid) {
        return Optional.ofNullable(map.get(rid));
    }
}
