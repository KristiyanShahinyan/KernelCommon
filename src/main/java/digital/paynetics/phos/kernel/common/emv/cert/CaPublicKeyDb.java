package digital.paynetics.phos.kernel.common.emv.cert;

public interface CaPublicKeyDb extends CaPublicKeyDbReadOnly {
    String getRid();

    void add(CaPublicKeyData caPublicKeyData);
}
