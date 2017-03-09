package goldenhammer.ticket_to_ride_client.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by jon on 2/22/17.
 */

public class Hand {
    private List<DestCard> destinationCards = new ArrayList<>();
    private List<TrainCard> trainCards = new ArrayList<>();
    private DrawnDestCards drawnDestinationCards;

    public Hand() {
        destinationCards = new ArrayList<>();
        trainCards = new ArrayList<>();
    }

    public Hand(List<DestCard> destinationCards, List<TrainCard> trainCards) {
        this.destinationCards = destinationCards;
        this.trainCards = trainCards;
    }
    public Hand(List<DestCard> destinationCards, List<TrainCard> trainCards, DrawnDestCards drawnDestinationCards) {
        this.destinationCards = destinationCards;
        this.trainCards = trainCards;
        this.drawnDestinationCards = drawnDestinationCards;
    }
    public List<DestCard> getDestinationCards() {
        return destinationCards;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void addTrainCards(List<TrainCard> drawnCards) {
        for(TrainCard card : drawnCards) {
            trainCards.add(card);
        }
    }

    public DrawnDestCards getDrawnDestinationCards() {
        return drawnDestinationCards;
    }

    public void setDrawnDestinationCards(DrawnDestCards drawnDestinationCards) {
        this.drawnDestinationCards = drawnDestinationCards;
    }

    public void addDestCard(List<DestCard> drawnCards) {
        for(DestCard card: drawnCards) {
            destinationCards.add(card);
        }
    }

    public void setDrawnDestinationCards(List<DestCard> drawnDestinationCards) {
        this.drawnDestinationCards = new DrawnDestCards(drawnDestinationCards);
    }

    public void moveDrawnDestCardToHand(List<DestCard> discardedCards) {
        drawnDestinationCards.discardDrawnDestCards(discardedCards);
        List<DestCard> remainingCards = drawnDestinationCards.getRemainingDestCards();
        for(int i=0; i<remainingCards.size(); i++) {
            destinationCards.add(remainingCards.get(i));
        }
    }

    public void addBankCard(TrainCard card) {
        trainCards.add(card);
    }

    //TODO: test how this removes Train cards from the player's hand
    public void removeTrainCards(List<TrainCard> cards) {
        Iterator<TrainCard> i = trainCards.iterator();
        int remainder = cards.size();

        while((i.hasNext()) && (remainder > 0)) {
            TrainCard card = i.next();
            for(TrainCard neededCard: cards) {
                if(card.equals(neededCard)) {
                    remainder = remainder - 1;
                    i.remove();
                }
            }
        }
    }
}
