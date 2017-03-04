package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class StateSelector {

    public static State MyTurn(GamePlayPresenter parent){
        return new State(parent) {
            @Override
            public void takeTrainCards() {

            }

            @Override
            public void takeDestCards() {
                presenter.sendTakeDestCardsCommand();
            }

            @Override
            public void takeTrack(Track track) {

            }
        };
    }

    public static State NotMyTurn(GamePlayPresenter parent) {
        return new State(parent);
    }

    public static State MustReturnDestCard(GamePlayPresenter parent){
        return new State(parent){
            @Override
            public void takeTrainCards() {

            }

            @Override
            public void takeDestCards() {
                this.presenter.sendTakeDestCardsCommand();
            }

            @Override
            public void takeTrack(Track track) {

            }
        };
    }

    public static State InitializeHand(GamePlayPresenter parent) {
        return new State(parent) {
            @Override
            public void returnDestCards(List<DestCard> toReturn) {
                presenter.sendReturnDestCardsCommand(toReturn);
            }

            @Override
            public void update() {
                super.update();

            }
        };
    }
}
