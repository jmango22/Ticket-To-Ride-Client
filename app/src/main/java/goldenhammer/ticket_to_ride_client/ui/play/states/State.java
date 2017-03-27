package goldenhammer.ticket_to_ride_client.ui.play.states;

import android.graphics.PointF;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;

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
    public void takeTrainCards(int index){
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


    public void clickTrainCards(){
        presenter.showToast("It isn't your turn.");
    }

    public void clickTracks(){
        presenter.showToast("It isn't your turn.");
    }

    public void clickDestCards(){
        presenter.showToast("It isn't your turn.");
    }

    public void updateView(){
        presenter.updateMap();
        presenter.updatePlayers();
        //presenter.updateCurrentTurn();
        presenter.updateHand();
        presenter.updateChat();
        presenter.updateBank();
    }

    public Track onTouchEvent(PointF pt, List<Track> tracks){ return null; }

}
