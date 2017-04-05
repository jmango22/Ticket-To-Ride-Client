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
    static GamePlayPresenter presenter;

    public static void setPresenter(GamePlayPresenter pre){
        presenter = pre; }

    public static State MyTurn(){
        return new MyTurnState(presenter);
    }

    public static State NotMyTurn() {
        return new NotMyTurnState(presenter);
    }

    public static State MustReturnDestCard(){
        return new ReturnDestCardsState(presenter);
    }

    public static State InitializeHand() {
        return new InitializeHandState(presenter);
    }

    public static State DrawSecondTrainCard(){
        return new DrawSecondTrainCardsState(presenter);
    }

    public static State Waiting(){  return new WaitingState(presenter);    }

    public static State EndGame(){
        return new EndGameState(presenter);
    }

    public static State BrokenGame(){
        return new BrokenGameState(presenter);
    }
}
