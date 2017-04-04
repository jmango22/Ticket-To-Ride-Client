package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.ui.play.states.InitializeHandState;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by McKean on 3/3/2017.
 */
public class ReturnDestCardsCommand extends BaseCommand {
    List<DestCard> toReturn;

    public ReturnDestCardsCommand(int commandNumber, List<DestCard> toReturn) {
        this.toReturn = toReturn;
        setName("ReturnDestCards");
        setCommandNumber(commandNumber);
    }
    @Override
    public void execute() {
        if (getPlayerNumber() == ClientModelFacade.SINGLETON.getMyPlayerNumber()) {
            ClientModelFacade model = ClientModelFacade.SINGLETON;
            Hand hand = model.getHand();
            for (DestCard card : hand.getDrawnDestinationCards().getRemainingDestCards()) {
                boolean keep = true;
                for (DestCard card2 : toReturn) {
                    if (card.equals(card2)) {
                        keep = false;
                    }
                }
                if (keep) {
                    hand.addSingleDestCard(card);
                }
            }
            hand.setDrawnDestinationCards(new ArrayList<DestCard>());
            setState();
        }
    }

    private void setState(){
        if(ClientModelFacade.SINGLETON.getState() instanceof InitializeHandState){
            ClientModelFacade.SINGLETON.setState(StateSelector.Waiting());
        }else {
            ClientModelFacade.SINGLETON.setState(StateSelector.NotMyTurn());
        }
    }
}
