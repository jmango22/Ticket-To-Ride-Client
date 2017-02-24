package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by McKean on 2/3/2017.
 * JavaDoc created by Jonathon Meng 2/22/2017
 */

/**
 * The ClientModelFacade provides a way to access the Games Client model through its singleton
 * pattern. This class keeps track of the game all the games available, as well as the games
 * that the player is a part of. This class handles all access to the Player class as well.
 * @invariant class follows the SINGLETON pattern
 * @invariant class extends Observable
 */


public class ClientModelFacade extends Observable {
    private GameList mAvailableGames = new GameList(new ArrayList<GameListItem>());
    private GameList mMyGames= new GameList(new ArrayList<GameListItem>());
    private GameModel mCurrentGame;
    private Player mUser;
    public static final ClientModelFacade SINGLETON = new ClientModelFacade();

    private ClientModelFacade(){
    }

    /**
     * @pre This class changes, and needs to update other classes that it holds.
     * @post The class is added as an observer to this class.
     * @param o a new class that will be updated when ClientModelFacade changes
     */
    public void addNewObserver(Observer o){
        addObserver(o);
    }

    /**
     * @pre This class is a singleton.
     * @post A single instance of this class is returned.
     * @return a single instance of the ClientModelFacade class
     */
    public synchronized ClientModelFacade getInstance(){
        return SINGLETON;
    }

    /**
     * @pre The player or games have changed.
     * @post The observers of this class know that it has changed.
     */
    public void changed(){
        setChanged();
    }

    /**
     * @pre There are available games on the client that the player can join.
     * @post All the available games on the client are returned.
     * @return List of all the games that the client holds that the player can join.
     */
    public GameList getAvailableGames() {
        return mAvailableGames;
    }

    /**
     * @pre There are available games for the player to join.
     * @post Sets all the available games to the new list. Notifies the observers that the games have changed.
     * @param games List of all the available games.
     */
    public void setAvailableGames(GameList games) {
        mAvailableGames = games;
        setChanged();
        notifyObservers();
    }

    /**
     * @pre The player is in at least one game.
     * @post Returns a list of the games the player is currently a member of.
     * @return List of all the games that the player is a part of.
     */
    public GameList getMyGames() {
        return mMyGames;
    }

    /**
     * @pre The player has joined at least one game.
     * @post Sets all the player's games to the new list. Notifies the observers that the player's games have changed.
     * @param mMyGames List of all the games that the player is a part of.
     */
    public void setMyGames(GameList mMyGames) {
        this.mMyGames = mMyGames;
        setChanged();
        notifyObservers();
    }

    /**
     * @pre The player is playing a game.
     * @post The game that the player is a part of is returned.
     * @return The current Game that the player is playing.
     */
    public GameModel getCurrentGame() {
        return mCurrentGame;
    }

    /**
     * @pre The player is currently not a member of any game.
     * @post Sets the current game to the new game. Tracks the new game state and notifies obversers that the new game started.
     * @param mCurrentGame is the current game the player has decided to play.
     */
    public void setCurrentGame(GameModel mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
        setChanged();
        notifyObservers();
    }

    /**
     * @pre The player's information is stored in the model.
     * @post The player is returned.
     * @return The current player.
     */
    public Player getUser() {
        return mUser;
    }

    /**
     * @pre The current player is done playing, and a new one is signing in.
     * @post The new player is set up in the model. The observers are notified that the player is different.
     * @param mUser is a new player object.
     */
    public void setUser(Player mUser) {
        this.mUser = mUser;
        setChanged();
        notifyObservers();
    }

    //TODO: add the destination card to the player's hand
    public void drawDestCards() {

    }

    //TODO: add train cards to the player's hand
    public void drawTrainCards() {

    }

    //TODO: remove Destination cards from the player's hand.
    public void removeDestCards(List<DestCard> cards) {

    }

    //TODO: remove Train cards from the player's hand
    public void removeTrainCards(List<TrainCard> cards) {

    }

    //TODO: add a bank card to the player's hand
    public Boolean takeBankCard(TrainCard card) {
        return false;
    }


    //TODO: the server claim a track for any player in the model
    public Boolean claimTrack(Track track, int player) {
        return false;
    }


    //TODO: update all visible player objects
    public void setPlayerHands(List<PlayerOverview> players) {

    }
}
