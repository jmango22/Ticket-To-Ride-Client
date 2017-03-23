package goldenhammer.ticket_to_ride_client.ui.play.states;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayPresenter;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class StateSelector {

    public static State MyTurn(GamePlayPresenter presenter){
        return new MyTurnState(presenter);
    }

    public static State NotMyTurn(GamePlayPresenter presenter) {
        return new NotMyTurnState(presenter);
    }

    public static State MustReturnDestCard(GamePlayPresenter presenter){
        return new ReturnDestCardsState(presenter);
    }

    public static State InitializeHand(GamePlayPresenter presenter) {
        return new InitializeHandState(presenter);
    }

    public static State DrawSecondTrainCard(GamePlayPresenter presenter){
        return new DrawSecondTrainCardsState(presenter);
    }

    public static State EndGame(GamePlayPresenter presenter){
        return new EndGameState(presenter);
    }
}
