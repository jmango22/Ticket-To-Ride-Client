package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class StateSelector {

    public static State MyTurn(GamePlayPresenter presenter){
        return new State(presenter) {
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
        };

    }

    public static State NotMyTurn(GamePlayPresenter presenter) {
        return new State(presenter);
    }

    public static State MustReturnDestCard(GamePlayPresenter presenter){
        return new State(presenter){
            @Override
            public void returnDestCards(List<DestCard> toReturn) {
                presenter.sendReturnDestCardsCommand(toReturn);
            }

            @Override
            public void updateView() {
                super.updateView();
                //TODO: show the dialog maybe not call super
            }
        };
    }

    public static State InitializeHand(GamePlayPresenter presenter) {
        return new State(presenter) {
            @Override
            public void returnDestCards(List<DestCard> toReturn) {
                presenter.sendReturnDestCardsCommand(toReturn);
            }

            @Override
            public void updateView() {
                super.updateView();
                presenter.initializeHand();
                //TODO: show the dialog maybe not call super
            }
        };
    }

    public static State DrawSecondTrainCard(GamePlayPresenter presenter){
        return new State(presenter){
            @Override
            public void takeTrainCards(int index) {
                presenter.sendTakeTrainCardsCommand(index);
            }

            @Override
            public void clickTrainCards() {
                presenter.startTrainCardsDialog();
            }

            @Override
            public void clickTracks() {
                presenter.showToast("You need to select a second train card.");
            }

            @Override
            public void clickDestCards() {
                presenter.showToast("You need to select a second train card.");
            }
        };


    }

    public static State EndGame(GamePlayPresenter presenter){
        return new State(presenter){
            @Override
            public void updateView() {
                presenter.endGame();
            }
        };
    }
}
