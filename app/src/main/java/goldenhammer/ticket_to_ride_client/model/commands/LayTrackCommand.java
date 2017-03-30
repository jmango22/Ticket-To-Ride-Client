package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.ArrayList;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;
import goldenhammer.ticket_to_ride_client.ui.play.states.MyTurnState;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by McKean on 3/3/2017.
 */
public class LayTrackCommand extends BaseCommand {
    Color[] cards;
    Track track;

    public LayTrackCommand() {
        setName("LayTrack");
    }

    public LayTrackCommand(int commandNumber) {
        setName("LayTrack");
        setCommandNumber(commandNumber);
    }

    @Override
    public void execute() {
            ArrayList<TrainCard> actualCards = new ArrayList<>();
            for (Color c : cards) {
                actualCards.add(new TrainCard(c));
            }
            ClientModelFacade.SINGLETON.changeTrackOwner(track, getPlayerNumber());
            ClientModelFacade.SINGLETON.addPoints(getPlayerNumber(),track.getPointValue());
        if(ClientModelFacade.SINGLETON.getMyPlayerNumber() == getPlayerNumber()) {
            ClientModelFacade.SINGLETON.removeTrainCards(actualCards);
            ClientModelFacade.SINGLETON.removePieces(track.getLength());
            setState();
        }
    }

    public void setCards(ArrayList<TrainCard> cards){
        Color[] cardColors = new Color[cards.size()];
        int size = 0;
        for(TrainCard c: cards){
            cardColors[size] = c.getColor();
            size++;
        }
        this.cards = cardColors;
    }

    public void setTrack(Track track){
        this.track = track;
    }

    private void setState(){
        if(ClientModelFacade.SINGLETON.getState() instanceof MyTurnState){
            ClientModelFacade.SINGLETON.setState(StateSelector.NotMyTurn());
        }
    }
}
