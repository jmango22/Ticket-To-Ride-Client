package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

/**
 * Created by McKean on 3/22/2017.
 */

public class NotMyTurnState extends State {
    public NotMyTurnState(GamePlayPresenter presenter) {
        super(presenter);
    }

    @Override
    public void updateView() {
        super.updateView();
        presenter.closeInitHandDialog();
        presenter.closeReturnDestCardsDialog();
        presenter.closeTrainCardsDialog();
        String name =ClientModelFacade.SINGLETON.getLeaderboard()
                .get(ClientModelFacade.SINGLETON.getCurrentTurnPlayer()).getUsername();
        presenter.updateTitle(name + "\'s Turn");
    }
}
