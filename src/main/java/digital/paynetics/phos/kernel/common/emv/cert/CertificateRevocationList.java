package digital.paynetics.phos.kernel.common.emv.cert;

public interface CertificateRevocationList extends CertificateRevocationListReadOnly {
    void add(String rid, int caPublicKeyIndex, byte[] serial);

    void add(String rid, String target);
}
