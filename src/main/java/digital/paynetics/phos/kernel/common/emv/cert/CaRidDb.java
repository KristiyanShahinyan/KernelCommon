package digital.paynetics.phos.kernel.common.emv.cert;

public interface CaRidDb extends CaRidDbReadOnly {
    void addDb(CaPublicKeyDb db);

    boolean exists(String rid);

    void clear();
}
