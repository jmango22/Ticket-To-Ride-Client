package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;

/**
 * Created by McKean on 4/5/2017.
 */

public class BrokenGameState extends State {
    public BrokenGameState(GamePlayPresenter presenter) {
        super(presenter);
    }

    @Override
    public void updateView() {
        super.updateView();
        presenter.updateTitle("Someone left the game, you should too.");
        presenter.closeInitHandDialog();
    }
}
