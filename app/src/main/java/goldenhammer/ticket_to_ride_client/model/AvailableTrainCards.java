package goldenhammer.ticket_to_ride_client.model;

/**
 * Created by jon on 3/1/17.
 */

public class AvailableTrainCards {
    private TrainCard[] availableCards;

    public AvailableTrainCards() {
        availableCards = new TrainCard[5];
    }

    public AvailableTrainCards(TrainCard[] availableCards) {
        this.availableCards = availableCards;
    }

    public void setAvailableCards(TrainCard[] trainCards) {
        this.availableCards = trainCards;
    }

    public TrainCard[] getAvailableCards() {
        return availableCards;
    }

    public TrainCard removeCard(int pos) {
        TrainCard card = availableCards[pos];
        availableCards[pos] = null;
        return card;
    }

    public void addCard(TrainCard card, int pos) {
        availableCards[pos] = card;
    }
}
