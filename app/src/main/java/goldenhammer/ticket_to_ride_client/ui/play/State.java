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

    }

    /**
     * defaults to not your turn
     */
    public void takeDestCards(){

    }

    /**
     * defaults to not your turn
     */
    public void returnDestCards(List<DestCard> toReturn){

    }

    /**
     * defaults to not your turn
     */
    public void takeTrack(Track track){

    }
    public void update(){
        presenter.updateMap();
        presenter.updatePlayers();
    }
}
