package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.model.commands.Command;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;

/**
 * Created by McKean on 2/3/2017.
 * JavaDoc created by Jonathon Meng 2/22/2017
 */

public class ClientModelFacade extends Observable {
    private GameList mAvailableGames = new GameList(new ArrayList<GameListItem>());
    private GameList mMyGames= new GameList(new ArrayList<GameListItem>());
    private GameModel mCurrentGame;
    private Bank mBank;
    private Player mUser;
    private CommandManager mCommandMgr = new CommandManager();
    public  static final  ClientModelFacade SINGLETON = new ClientModelFacade();

    private ClientModelFacade(){
    }

    public void addNewObserver(Observer o){
        addObserver(o);
    }

    public synchronized ClientModelFacade getInstance(){
        return SINGLETON;
    }

    public void changed(){
        setChanged();
        notifyObservers();
    }

    public GameList getAvailableGames() {
        return mAvailableGames;
    }

    public void setAvailableGames(GameList games) {
        mAvailableGames = games;
        changed();
    }

    public GameList getMyGames() {
        return mMyGames;
    }

    public void setMyGames(GameList mMyGames) {
        this.mMyGames = mMyGames;
        changed();
    }

    public GameModel getCurrentGame() {
        return mCurrentGame;
    }

    public void setCurrentGame(GameModel mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
        changed();
    }

    public Player getUser() {
        return mUser;
    }

    public void setUser(Player mUser) {
        this.mUser = mUser;
        changed();
    }


    /**
     * Presenter Code
     */
    //get user train cards
    public List<TrainCard> getUserTrainCards() {
        return mUser.getTrainCards();
    }

    //get user destination cards
    public List<DestCard> getUserDestCards() {
        return mUser.getDestinationCards();
    }

    //add train cards to the player's hand
    public void drawTrainCards(List<TrainCard> drawnCards) {
        mUser.addTrainCards(drawnCards);
        changed();
    }

    //remove Train cards from the player's hand
    public void removeTrainCards(List<TrainCard> cards) {
        mUser.removeTrainCards(cards);
        changed();
    }

    //add a bank card to the player's hand
    public void takeBankCard(int pos) {
        TrainCard temp;
        if((temp = mBank.getTrainCard(pos)) != null) {
            mUser.addBankCard(temp);
            changed();
        }
    }

    public void setDrawnDestCards(List<DestCard> cards) {
        mUser.setDrawDestCards(cards);
        changed();
    }

    public void moveDrawnDestCardsToHand(List<DestCard> discardedCards) {
        mUser.moveDrawnDestCards(discardedCards);
        changed();
    }

    public void claimTrack(Track track, int player) {
        mCurrentGame.claimTrack(track, player);
        changed();
    }

    public List<Track> getAllTracks() {
        return mCurrentGame.getAllTracks();
    }

    public List<City> getAllCities() { return mCurrentGame.getAllCities(); }

    //update all visible player objects
    public void setLeaderboard(List<PlayerOverview> players) {
        mCurrentGame.setLeaderBoard(players);
        changed();
    }

    //get all visible player objects
    public List<PlayerOverview> getLeaderboard() {
        return mCurrentGame.getLeaderBoard();
    }


    public void replaceBankTrainCard(TrainCard card, int pos) {
        mBank.replaceAvailableTrainCard(card, pos);
        changed();
    }

    public List<TrainCard> getAllBankTrainCards() {
        TrainCard[] bankCards = mBank.getAvailableTrainCards();
        List<TrainCard> cards = new ArrayList<>();
        for(int i = 0; i<bankCards.length; i++) {
            cards.add(bankCards[i]);
        }
        return cards;
    }


    //END PRESENTER CODE

    /**
     * Command Manager Code
     */

    public Command getPreviousCommand() {
        List<Command> commands = mCommandMgr.getCommandList();
        return commands.get(commands.size() - 1);
    }

    public int getNextCommandNumber() {
        return mCommandMgr.getCommandList().size();
    }

    public boolean isMyTurn() {
        //TODO: return true if it is my turn Need to know My Player Number
        return true;
    }

    //TODO: get this to work

    /**
     *
     * @return the playerNumber of the player whose turn it is
     */
    public int getCurrentTurnPlayer() {
        return 1;
    }

    /**
     *
     * @return the playerNumber of the player using this device
     */
    public int getMyPlayerNumber() {
        //TODO: this won't work. make it work
        return 1;
    }
    public Hand getHand() {
        return mUser.getHand();
    }
    public boolean shouldInitializeHand() {
        List<Command> commands = mCommandMgr.getCommandList();
        if(commands.size() > getCurrentGame().getLeaderBoard().size() * 2){
            return false;
        }
        //TODO: this won't work. We need a way to know My Player number
        int myNumber = getMyPlayerNumber();
        for (Command command: commands){
            if (command.getPlayerNumber() == myNumber && command instanceof ReturnDestCardsCommand){
                return true;
            }
        }
        return false;
    }

    public void addCommands(List<Command> newCommands) {
        mCommandMgr.addCommands(newCommands);
    }

    //END COMMAND MANAGER CODE

    /**
     *
     * initializing code
     */

    public void setCities(List<City> cities) {
        mCurrentGame.setCities(cities);
        changed();
    }

    public void setTracks(List<Track> tracks) {
        mCurrentGame.setTracks(tracks);
        changed();
    }

    public void setBankCards(TrainCard[] trainCards) {
        mBank = new Bank(trainCards);
        changed();
    }

    //END INITIALIZING CODE

}
