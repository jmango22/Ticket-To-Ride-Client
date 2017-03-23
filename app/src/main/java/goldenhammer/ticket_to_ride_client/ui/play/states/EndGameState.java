package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

/**
 * Created by McKean on 3/22/2017.
 */

public class EndGameState extends State {
    public EndGameState(GamePlayPresenter presenter) {
        super(presenter);
    }
    @Override
    public void updateView() {
        presenter.endGame();
    }
}
