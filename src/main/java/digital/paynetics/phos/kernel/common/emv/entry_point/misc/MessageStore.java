package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import java8.util.Optional;


public interface MessageStore {
    void set(UserInterfaceRequest ui);
    Optional<UserInterfaceRequest> get();
}
