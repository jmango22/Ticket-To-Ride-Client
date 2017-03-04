package goldenhammer.ticket_to_ride_client.ui.play;

import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class ActionSelector {

    public static DoAction MyTurn(GamePlayPresenter parent){
        return new DoAction(parent) {
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

    public static DoAction NotMyTurn(GamePlayPresenter parent) {
        return new DoAction(parent);
    }

    public static DoAction MustReturnDestCard(GamePlayPresenter parent){
        return new DoAction(parent){
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
}
