package goldenhammer.ticket_to_ride_client.model;

import java.util.List;

/**
 * Created by jon on 3/1/17.
 */

public class Bank {
    private AvailableTrainCards availableTrainCards;

    public Bank() {
        availableTrainCards = new AvailableTrainCards();
    }

    public Bank(TrainCard[] trainCards) {
        this.availableTrainCards = new AvailableTrainCards(trainCards);
    }

    public TrainCard[] getAvailableTrainCards() {
        return availableTrainCards.getAvailableCards();
    }

    public void setAvailableTrainCards(TrainCard[] trainCards) {
        this.availableTrainCards.setAvailableCards(trainCards);
    }

    public void replaceAvailableTrainCard(TrainCard card, int pos) {
        this.availableTrainCards.addCard(card, pos);
    }

    public TrainCard getTrainCard(int pos) {
        return this.availableTrainCards.removeCard(pos);
    }
}
