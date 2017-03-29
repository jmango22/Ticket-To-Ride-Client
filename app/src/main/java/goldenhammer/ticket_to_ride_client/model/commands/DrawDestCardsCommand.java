package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.ui.play.states.MyTurnState;
import goldenhammer.ticket_to_ride_client.ui.play.states.ReturnDestCardsState;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by jon on 3/2/17.
 */
public class DrawDestCardsCommand extends BaseCommand {
    private List<DestCard> cards;

    public DrawDestCardsCommand(){
        setName("DrawDestCards");
    }
    public DrawDestCardsCommand(int CommandNumber){
        setName("DrawDestCards");
        setCommandNumber(CommandNumber);
    }

    @Override
    public void execute() {
        if(ClientModelFacade.SINGLETON.getMyPlayerNumber() == getPlayerNumber()) {
            ClientModelFacade.SINGLETON.setDrawnDestCards(cards);
            setState();
        }
    }

    public void setCards(List<DestCard> cards){
        this.cards = cards;
    }

    private void setState(){
        if(ClientModelFacade.SINGLETON.getState() instanceof MyTurnState){
            ClientModelFacade.SINGLETON.setState(StateSelector.MustReturnDestCard());
        }
    }
}
