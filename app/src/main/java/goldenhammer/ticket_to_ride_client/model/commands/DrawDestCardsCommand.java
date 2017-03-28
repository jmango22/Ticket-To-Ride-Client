package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;

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
        ClientModelFacade.SINGLETON.setDrawnDestCards(cards);
    }

    public void setCards(List<DestCard> cards){
        this.cards = cards;
    }
}
