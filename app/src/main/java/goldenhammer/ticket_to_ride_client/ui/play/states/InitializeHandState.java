package goldenhammer.ticket_to_ride_client.ui.play.states;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

/**
 * Created by McKean on 3/22/2017.
 */

public class InitializeHandState extends State {
    public InitializeHandState(GamePlayPresenter presenter) {
        super(presenter);
    }
    @Override
    public void returnDestCards(List<DestCard> toReturn) {
        presenter.sendReturnDestCardsCommand(toReturn);
    }

    @Override
    public void updateView() {
        super.updateView();
        presenter.updateTitle("Initializing Hand");
        presenter.initializeHand();
        //TODO: show the dialog maybe not call super
    }


}
