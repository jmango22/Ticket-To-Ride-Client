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


    public DrawnDestCards getDrawnDestCards() {
        return drawnDestinationCards;
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

    public void addSingleDestCard(DestCard card){
        destinationCards.add(card);
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

    public boolean removeTrainCards(List<TrainCard> cards) {
         boolean success = true;
        for(int i = 0 ; i < cards.size(); i++){
            success = trainCards.remove(cards.get(i));
            if(!success){
                break;
            }
        }
        return success;
    }

    public boolean enoughTrainCards(int number){
        int wild = getWildTrainCards();
        if ((getRedTrainCards() + wild >= number) || (getOrangeTrainCards() + wild >= number)
                || (getYellowTrainCards() + wild >= number) || (getGreenTrainCards() + wild >= number)
                || (getBlueTrainCards() + wild >= number) || (getWhiteTrainCards() + wild >= number)
                || (getBlackTrainCards() + wild >= number) || (getPinkTrainCards() + wild >= number)) {
            return true;
        } else {
            return false;
        }
    }

    public int getRedTrainCards(){
        int red = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.RED) {
                red++;
            }
        }
        return red;
    }

    public int getOrangeTrainCards(){
        int orange= 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.ORANGE) {
                orange++;
            }
        }
        return orange;
    }

    public int getYellowTrainCards(){
        int yellow = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.YELLOW) {
                yellow++;
            }
        }
        return yellow;
    }

    public int getGreenTrainCards(){
        int green = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.GREEN) {
                green++;
            }
        }
        return green;
    }

    public int getBlueTrainCards(){
        int blue = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.BLUE) {
                blue++;
            }
        }
        return blue;
    }

    public int getWhiteTrainCards(){
        int white = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.WHITE) {
                white++;
            }
        }
        return white;
    }

    public int getBlackTrainCards(){
        int black = 0;
        for (TrainCard t : trainCards) {
           if (t.getColor() == Color.BLACK) {
                black++;
            }
        }
        return black;
    }

    public int getPinkTrainCards(){
        int pink = 0;
        for (TrainCard t : trainCards) {
        if (t.getColor() == Color.PURPLE) {
                pink++;
            }
        }
        return pink;
    }

    public int getWildTrainCards(){
        int wild = 0;
        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.WILD) {
                wild++;
            }
        }
        return wild;
    }
}
