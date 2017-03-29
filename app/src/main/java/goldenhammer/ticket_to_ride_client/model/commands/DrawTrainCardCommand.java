package goldenhammer.ticket_to_ride_client.model.commands;


import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.TrainCard;
import goldenhammer.ticket_to_ride_client.ui.play.states.DrawSecondTrainCardsState;
import goldenhammer.ticket_to_ride_client.ui.play.states.MyTurnState;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by seanjib on 3/1/2017.
 */
public class DrawTrainCardCommand extends BaseCommand {
    private TrainCard card;
    private int slot;
    private Color drawnCard;
    private List<Color> bank;

    public DrawTrainCardCommand(){
        setName("DrawTrainCard");
    }

    public DrawTrainCardCommand(int commandNumber){
        setName("DrawTrainCard");
        setCommandNumber(commandNumber);
    }

    public void execute() {
        if(ClientModelFacade.SINGLETON.getMyPlayerNumber() == getPlayerNumber()) {
            ClientModelFacade.SINGLETON.addTrainCard(card);
            TrainCard[] bankCards = new TrainCard[bank.size()];
            for (int i = 0; i < bank.size(); i++) {
                bankCards[i] = new TrainCard(bank.get(i));
            }
            ClientModelFacade.SINGLETON.setBankCards(bankCards);
            setState();
        }
    }

    public void setCard(TrainCard card){
        this.card = card;
    }

    public void setSlot(int slot){
        this.slot = slot;
    }

    private void setState(){
        if(ClientModelFacade.SINGLETON.getState() instanceof MyTurnState){
            if((card.getColor() == Color.WILD)&&(slot != -1)){
                ClientModelFacade.SINGLETON.setState(StateSelector.NotMyTurn());
            }else {
                ClientModelFacade.SINGLETON.setState(StateSelector.DrawSecondTrainCard());
            }
        }else if (ClientModelFacade.SINGLETON.getState() instanceof DrawSecondTrainCardsState){
            ClientModelFacade.SINGLETON.setState(StateSelector.NotMyTurn());
        }
    }

}
