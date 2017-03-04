package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class DoAction {
    protected GamePlayPresenter presenter;
    public DoAction(GamePlayPresenter presenter) {
        this.presenter = presenter;
    }

    public void takeTrainCards(){

    }

    public void takeDestCards(){

    }

    public void returnDestCards(List<DestCard> toReturn){

    }

    public void takeTrack(Track track){

    }
}
