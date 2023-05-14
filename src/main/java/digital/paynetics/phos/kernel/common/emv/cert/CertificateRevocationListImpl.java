package digital.paynetics.phos.kernel.common.emv.cert;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static digital.paynetics.phos.kernel.common.emv.cert.RidCertificateRevocationListImpl.buildTarget;
import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


public class CertificateRevocationListImpl implements CertificateRevocationList {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Set<String>> ridMap = new HashMap<>();


    @Override
    public void add(String rid, int caPublicKeyIndex, byte[] serial) {
        if (serial.length != 3) {
            throw new IllegalArgumentException("serial.length != 3");
        }

        String target = buildTarget(caPublicKeyIndex, serial);
        add(rid, target);
    }


    @Override
    public void add(String rid, String target) {
        if (rid.length() != 10) {
            throw new IllegalArgumentException("rid.length() != 10");
        }

        if (target.length() != 8) {
            throw new IllegalArgumentException("target.length() != 8");
        }

        Set<String> set = ridMap.get(rid);
        if (set == null) {
            set = new HashSet<>();
            ridMap.put(rid, set);
        } else {
            if (set.contains(target)) {
                throw new IllegalArgumentException(format("Item {} already added to RID {}.", target, rid));
            }
        }

        set.add(target);
    }


    @Override
    public boolean isPresent(String rid, int caPublicKeyIndex, byte[] serial) {
        Set<String> set = ridMap.get(rid);
        if (set == null) {
            logger.warn("isPresent() called with unknown RID: {}", rid);
            return false;
        }

        return set.contains(buildTarget(caPublicKeyIndex, serial));
    }


    @Override
    public RidCertificateRevocationList slice(String rid) {
        Set<String> slice = ridMap.get(rid);
        if (slice != null) {
            return new RidCertificateRevocationListImpl(slice);
        } else {
            return new RidCertificateRevocationListImpl(new HashSet<>());
        }
    }
}
