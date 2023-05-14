package digital.paynetics.phos.kernel.common.emv.cert;


import java8.util.Optional;


public interface CaPublicKeyDbReadOnly {
    Optional<CaPublicKeyData> getByIndex(int index);

    boolean exists(int index);
}
