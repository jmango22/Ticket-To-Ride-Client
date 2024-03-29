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
    public boolean takeTrainCards(int index) {
            presenter.sendTakeTrainCardsCommand(index);
            return false;
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
            presenter.showToast("Select a track to claim.");
        }else{
            layTrack=false;
        }
    }

    @Override
    public void clickDestCards() {
        presenter.sendTakeDestCardsCommand();
    }

    public Track onTouchEvent(PointF pt, List<Track> tracks){
        if(layTrack) {
            int tolerance = 10;
            Track closest = null;
            double minDistance = 100;
            for (Track t : tracks) {
                double distance = t.pointByLine(pt);
                if(distance < minDistance){
                    minDistance = distance;
                    closest = t;
                }
//                if (t.pointByLine(pt, tolerance)) {
//                    return t;
//                }
            }
            return closest;
        }
        return null;
    }

    @Override
    public void updateView() {
        super.updateView();
        presenter.closeInitHandDialog();
        presenter.updateTitle("Your Turn");
    }
}

