package digital.paynetics.phos.kernel.common.emv.cert;

import java8.util.Optional;


public interface CaRidDbReadOnly {
    Optional<CaPublicKeyDb> getByRid(String rid);

}
