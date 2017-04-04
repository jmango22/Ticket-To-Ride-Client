package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;

/**
 * Created by McKean on 4/3/2017.
 */

public class WaitingState extends State {
    public WaitingState(GamePlayPresenter presenter) {
        super(presenter);
    }
    @Override
    public void updateView() {
        super.updateView();
        presenter.updateTitle("Waiting for other players");
        presenter.closeInitHandDialog();
    }
}
