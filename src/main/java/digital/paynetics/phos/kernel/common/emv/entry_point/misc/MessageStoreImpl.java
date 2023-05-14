package digital.paynetics.phos.kernel.common.emv.entry_point.misc;

import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import java8.util.Optional;


public class MessageStoreImpl implements MessageStore {
    private UserInterfaceRequest ui;


    @Override
    public void set(UserInterfaceRequest ui) {
        this.ui = ui;
    }


    @Override
    public Optional<UserInterfaceRequest> get() {
        return Optional.ofNullable(ui);
    }
}
