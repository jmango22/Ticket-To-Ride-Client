package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;

/**
 * Created by McKean on 3/3/2017.
 */

public class LayTrackCommand extends Command {
    Track track;
    ArrayList<TrainCard> cards;

    public LayTrackCommand(int commandNumber) {
        super(commandNumber);
    }

    public void setTrack(Track track){
        this.track = track;
    }

    public void setCards(ArrayList<TrainCard> cards){
        this.cards = cards;
    }

    @Override
    public void execute() {
        Hand hand = ClientModelFacade.SINGLETON.getHand();
        hand.removeTrainCards(cards);

    }
}
