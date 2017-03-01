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

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void addTrainCards(List<TrainCard> drawnCards) {
        for(TrainCard card : drawnCards) {
            trainCards.add(card);
        }
    }

    public void addDestCards(List<DestCard> drawnCards) {
        for(DestCard card: drawnCards) {
            destinationCards.add(card);
        }
    }

    public void addBankCard(TrainCard card) {
        trainCards.add(card);
    }

    //TODO: test how this removes Destination cards from the player's hand.
    public void removeDestCards(List<DestCard> cards) {
        Iterator<DestCard> i = destinationCards.iterator();
        int remainder = cards.size();

        while((i.hasNext()) && (remainder > 0)) {
            DestCard card = i.next();
            for(DestCard neededCard: cards) {
                if(card.equals(neededCard)) {
                    remainder = remainder - 1;
                    i.remove();
                }
            }
        }
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