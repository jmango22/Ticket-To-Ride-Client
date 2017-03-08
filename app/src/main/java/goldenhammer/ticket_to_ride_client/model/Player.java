package goldenhammer.ticket_to_ride_client.model;

import java.util.Iterator;
import java.util.List;

/**
 * Created by McKean on 2/3/2017.
 */

public class Player {
    private Username mUsername;
    private Password mPassword;
    private Hand mHand;
    private int mPlayerNumber;

    public Player(Username username, Password password){
        mUsername = username;
        mPassword = password;
        mHand = new Hand();
    }

    public Username getUsername(){
        return mUsername;
    }

    public Password getPassword(){
        return mPassword;
    }

    public int getPlayerNumber() {
        return mPlayerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.mPlayerNumber = mPlayerNumber;
    }

    public List<DestCard> getDestinationCards() {
        return mHand.getDestinationCards();
    }

    public List<TrainCard> getTrainCards() {
        return mHand.getTrainCards();
    }

    public void addTrainCards(List<TrainCard> drawnCards) {
        mHand.addTrainCards(drawnCards);
    }

    public void addBankCard(TrainCard card) {
        mHand.addBankCard(card);
    }

    public void removeTrainCards(List<TrainCard> cards) {
        mHand.removeTrainCards(cards);
    }

    public void setDrawDestCards(List<DestCard> cards) {
        mHand.setDrawnDestCards(cards);
    }

    public void moveDrawnDestCards(List<DestCard> discardedCards) {
        mHand.moveDrawnDestCardToHand(discardedCards);
    }
    public Hand getHand(){
        return mHand;
    }
}
