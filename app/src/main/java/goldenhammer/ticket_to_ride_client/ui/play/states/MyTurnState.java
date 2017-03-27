package goldenhammer.ticket_to_ride_client.ui.play.states;

import android.graphics.PointF;

import java.util.List;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;

/**
 * Created by McKean on 3/22/2017.
 */

public class MyTurnState extends State {
    boolean layTrack;
    public MyTurnState(GamePlayPresenter presenter) {
        super(presenter);
    }

    @Override
    public void takeDestCards()
    {
            presenter.sendTakeDestCardsCommand();
    }

    @Override
    public void takeTrainCards(int index) {
            presenter.sendTakeTrainCardsCommand(index);
    }

    @Override
    public void layTrack(Track track) {
        layTrack = false;
        presenter.sendLayTrackCommand(track);
    }

    @Override
    public void clickTrainCards() {
        presenter.startTrainCardsDialog();
    }

    @Override
    public void clickTracks() {
        if(!layTrack) {
            layTrack = true;
            presenter.showToast("Select a train to claim.");
        }else{
            layTrack=false;
        }
    }

    @Override
    public void clickDestCards() {
        presenter.startDestCardsDialog();
    }

    public Track onTouchEvent(PointF pt, List<Track> tracks){
        if(layTrack) {
            int tolerance = 6;
            for (Track t : tracks) {
                if (t.pointByLine(pt, tolerance)) {
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public void updateView() {
        super.updateView();
        presenter.updateTitle("Your Turn");
    }
}

