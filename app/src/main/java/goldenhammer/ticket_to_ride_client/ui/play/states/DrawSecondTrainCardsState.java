package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

/**
 * Created by McKean on 3/22/2017.
 */

public class DrawSecondTrainCardsState extends State {
    public DrawSecondTrainCardsState(GamePlayPresenter presenter) {
        super(presenter);
    }

    @Override
    public void takeTrainCards(int index) {
        presenter.sendTakeTrainCardsCommand(index);
    }

    @Override
    public void clickTrainCards() {
        presenter.startTrainCardsDialog();
    }

    @Override
    public void clickTracks() {
        presenter.showToast("You need to select a second train card.");
    }

    @Override
    public void clickDestCards() {
        presenter.showToast("You need to select a second train card.");
    }
}
