package digital.paynetics.phos.kernel.common.emv.cert;


public class CrlRidImpl implements CrlRid {
    private final String rid;
    private final CertificateRevocationListReadOnly crl;


    public CrlRidImpl(String rid, CertificateRevocationListReadOnly crl) {
        this.rid = rid;
        this.crl = crl;
    }


    @Override
    public boolean isPresent(int caPublicKeyIndex, byte[] serial) {
        return crl.isPresent(rid, caPublicKeyIndex, serial);
    }
}

