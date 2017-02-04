package goldenhammer.ticket_to_ride_client;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by McKean on 2/3/2017.
 */

public class ClientModelFacade extends Observable {
    private GameList mAvailableGames;
    private GameList mMyGames;
    private GameModel mCurrentGame;
    private Player mUser;
    public static final ClientModelFacade SINGLETON = new ClientModelFacade();

    private ClientModelFacade(){
    }

    public void addNewObserver(Observer o){
        addObserver(o);
    }

    public ClientModelFacade getInstance(){
        return SINGLETON;
    }

    public void changed(){
        setChanged();
    }

    public GameList getAvailableGames() {
        return mAvailableGames;
    }

    public void setAvailableGames(GameList mAvailableGames) {
        this.mAvailableGames = mAvailableGames;
        setChanged();
        notifyObservers();
    }

    public GameList getMyGames() {
        return mMyGames;
    }

    public void setMyGames(GameList mMyGames) {
        this.mMyGames = mMyGames;
        setChanged();
        notifyObservers();
    }

    public GameModel getCurrentGame() {
        return mCurrentGame;
    }

    public void setCurrentGame(GameModel mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
        setChanged();
        notifyObservers();
    }

    public Player getUser() {
        return mUser;
    }

    public void setUser(Player mUser) {
        this.mUser = mUser;
        setChanged();
        notifyObservers();
    }
}
