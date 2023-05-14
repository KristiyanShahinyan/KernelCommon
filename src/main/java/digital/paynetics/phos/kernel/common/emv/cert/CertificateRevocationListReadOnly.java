package digital.paynetics.phos.kernel.common.emv.cert;

public interface CertificateRevocationListReadOnly {
    boolean isPresent(String rid, int caPublicKeyIndex, byte[] serial);

    RidCertificateRevocationList slice(String rid);
}
