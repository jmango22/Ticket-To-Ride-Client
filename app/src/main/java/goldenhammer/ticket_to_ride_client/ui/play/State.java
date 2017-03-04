package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class State {
    protected GamePlayPresenter presenter;
    public State(GamePlayPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * defaults to not your turn
     */
    public void takeTrainCards(){
        presenter.showToast("Can't do that");
    }

    /**
     * defaults to not your turn
     */
    public void takeDestCards(){
        presenter.showToast("Can't do that");
    }

    /**
     * defaults to not your turn
     */
    public void returnDestCards(List<DestCard> toReturn){
        presenter.showToast("Can't do that");
    }

    /**
     * defaults to not your turn
     */
    public void layTrack(Track track){
        presenter.showToast("Can't do that");
    }
    public void updateView(){
        presenter.updateMap();
        presenter.updatePlayers();
        presenter.updateCurrentTurn();
        presenter.updateHand();
    }
}