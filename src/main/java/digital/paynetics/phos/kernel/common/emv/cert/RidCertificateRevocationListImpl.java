package digital.paynetics.phos.kernel.common.emv.cert;

import java.util.Set;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;


public class RidCertificateRevocationListImpl implements RidCertificateRevocationList {
    private Set<String> set;


    public RidCertificateRevocationListImpl(Set<String> set) {
        this.set = set;
    }


    @Override
    public boolean isPresent(int caPublicKeyIndex, byte[] serial) {
        return set.contains(buildTarget(caPublicKeyIndex, serial));
    }


    static String buildTarget(int caPublicKeyIndex, byte[] serial) {
        byte[] tmp = new byte[4];
        tmp[0] = (byte) caPublicKeyIndex;
        tmp[1] = serial[0];
        tmp[2] = serial[1];
        tmp[3] = serial[2];

        return ByteUtils.toHexString(tmp);
    }
}
