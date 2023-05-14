package digital.paynetics.phos.kernel.common.emv.cert;

public interface RidCertificateRevocationList {
    boolean isPresent(int caPublicKeyIndex, byte[] serial);
}
