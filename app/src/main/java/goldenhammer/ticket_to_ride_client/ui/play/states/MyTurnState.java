package goldenhammer.ticket_to_ride_client.ui.play.states;

import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;

/**
 * Created by McKean on 3/22/2017.
 */

public class MyTurnState extends State {
    public MyTurnState(GamePlayPresenter presenter) {
        super(presenter);
    }

    @Override
    public void takeDestCards() {
        presenter.sendTakeDestCardsCommand();
    }

    @Override
    public void takeTrainCards(int index) {
        presenter.sendTakeTrainCardsCommand(index);
    }

    @Override
    public void layTrack(Track track) {
        presenter.sendLayTrackCommand(track);
        //consider how the cards are going to be taken from player's hand when they build.
    }

    @Override
    public void clickTrainCards() {
        presenter.startTrainCardsDialog();
    }

    @Override
    public void clickTracks() {
        presenter.startTracksDialog();
    }

    @Override
    public void clickDestCards() {
        presenter.startDestCardsDialog();
    }
}
