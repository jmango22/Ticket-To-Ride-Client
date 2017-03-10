package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Hand;

/**
 * Created by McKean on 3/3/2017.
 */

public class ReturnDestCardsCommand extends Command {
    List<DestCard> toReturn;
    public ReturnDestCardsCommand(int commandNumber, List<DestCard> toReturn) {
        super(commandNumber);
        this.toReturn = toReturn;
        this.setName("ReturnDestCards");
    }
    @Override
    public void execute() {
        ClientModelFacade model = ClientModelFacade.SINGLETON;
        Hand hand = model.getHand();
        for(DestCard card : hand.getDrawnDestinationCards().getRemainingDestCards()) {
            boolean keep = true;
            for(DestCard card2: toReturn){
                if(card.equals(card2)){
                    keep = false;
                }
            }
            if(keep) {
                hand.addSingleDestCard(card);
            }
        }
        hand.setDrawnDestinationCards(new ArrayList<DestCard>());
    }
}
