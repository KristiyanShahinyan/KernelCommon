package digital.paynetics.phos.kernel.common.emv.entry_point;

import java.util.List;

import digital.paynetics.phos.kernel.common.emv.Outcome;
import digital.paynetics.phos.kernel.common.emv.tag.Tlv;
import digital.paynetics.phos.kernel.common.emv.ui.UserInterfaceRequest;
import java8.util.Optional;


public class FinalOutcomeRetainedParameters {
    private final Outcome.Start start;
    private final Optional<UserInterfaceRequest> uiRequestOnRestartPresent;
    private final Optional<List<Tlv>> onlineResponseData;


    public FinalOutcomeRetainedParameters(Outcome.Start start,
                                          Optional<UserInterfaceRequest> uiRequestOnRestartPresent,
                                          Optional<List<Tlv>> onlineResponseData) {


        this.start = start;
        this.uiRequestOnRestartPresent = uiRequestOnRestartPresent;
        this.onlineResponseData = onlineResponseData;
    }


    public Outcome.Start getStart() {
        return start;
    }


    public Optional<UserInterfaceRequest> getUiRequestOnRestartPresent() {
        return uiRequestOnRestartPresent;
    }


    public Optional<List<Tlv>> getOnlineResponseData() {
        return onlineResponseData;
    }
}
