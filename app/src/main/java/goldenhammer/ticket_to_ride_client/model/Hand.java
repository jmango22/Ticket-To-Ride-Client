package goldenhammer.ticket_to_ride_client.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by jon on 2/22/17.
 */

public class Hand {
    private List<DestCard> destinationCards;
    private List<TrainCard> trainCards;

    public Hand() {
        destinationCards = new ArrayList<>();
        trainCards = new ArrayList<>();
    }

    public Hand(List<DestCard> destinationCards, List<TrainCard> trainCards) {
        this.destinationCards = destinationCards;
        this.trainCards = trainCards;
    }

    public List<DestCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(List<DestCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    //TODO: return unwanted Destination Cards back to the Server
    public Boolean returnDestCards(List<DestCard> cards) {
        return false;
    }

    //TODO: request more train cards from the bank
    public void addTrainCards(int number) {

    }

    //TODO: request one of the face up cards from the bank
    public Boolean addBankCard(TrainCard card) {
        return false;
    }

    //TODO: create a good test for this.
    public Boolean hasNeededTrainCards(Color color, int number) {
        int numberCount = 0;
        for(TrainCard card : trainCards) {
            if(card.getColor() == color) {
                numberCount = numberCount+1;
            }
        }
        if(numberCount == number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @post the cards are removed from the players hand and returned to the caller
     * @param color the color of the cards needed
     * @param number the number of the cards needed
     * @return
     */
    //TODO: Create a good test for this.
    public List<TrainCard> getNeededTrainCards(Color color, int number) {
        List<TrainCard> neededCards = new ArrayList<>();
        Iterator<TrainCard> i = neededCards.iterator();
        int remainder = number;

        while (i.hasNext()) {
            TrainCard card = i.next();
            if(card.getColor() == color) {
                neededCards.add(card);
                remainder = remainder - 1;
                i.remove();
            }
        }

        return neededCards;
    }

}
