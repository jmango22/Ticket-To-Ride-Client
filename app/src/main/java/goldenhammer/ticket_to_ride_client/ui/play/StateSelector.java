package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;

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
}
