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

    public synchronized void addNewObserver(Observer o){
        addObserver(o);
    }

    public synchronized ClientModelFacade getInstance(){
        return SINGLETON;
    }

    public void changed(){
        setChanged();
        notifyObservers();
    }

    public synchronized GameList getAvailableGames() {
        return mAvailableGames;
    }

    public synchronized void setAvailableGames(GameList games) {
        if(!mAvailableGames.equals(games)) {
            mAvailableGames = games;
            changed();
        }
    }

    public synchronized GameList getMyGames() {
        return mMyGames;
    }

    public synchronized void setMyGames(GameList mMyGames) {
        if(!this.mMyGames.equals(mMyGames)) {
            this.mMyGames = mMyGames;
            changed();
        }
    }

    public synchronized GameModel getCurrentGame() {
        return mCurrentGame;
    }

    public synchronized void setCurrentGame(GameModel mCurrentGame) {
        this.mCurrentGame = mCurrentGame;
        changed();
    }

    public synchronized Player getUser() {
        return mUser;
    }

    public synchronized void setUser(Player mUser) {
        this.mUser = mUser;
        changed();
    }


    /**
     * Presenter Code
     */
    //get user train cards
    public synchronized List<TrainCard> getUserTrainCards() {
        return mUser.getTrainCards();
    }

    //get user destination cards
    public synchronized List<DestCard> getUserDestCards() {
        return mUser.getDestinationCards();
    }

    //add train cards to the player's hand
    public synchronized void drawTrainCards(List<TrainCard> drawnCards) {
        mUser.addTrainCards(drawnCards);
        changed();
    }

    //remove Train cards from the player's hand
    public synchronized void removeTrainCards(List<TrainCard> cards) {
        mUser.removeTrainCards(cards);
        changed();
    }

    //add a bank card to the player's hand
    public synchronized void takeBankCard(int pos) {
        TrainCard temp;
        if((temp = mBank.getTrainCard(pos)) != null) {
            mUser.addBankCard(temp);
            changed();
        }
    }

    public synchronized void setDrawnDestCards(List<DestCard> cards) {
        mUser.setDrawDestCards(cards);
        changed();
    }

    public synchronized void moveDrawnDestCardsToHand(List<DestCard> discardedCards) {
        mUser.moveDrawnDestCards(discardedCards);
        changed();
    }

    public synchronized void claimTrack(Track track, int player) {
        mCurrentGame.claimTrack(track, player);
        changed();
    }

    public synchronized List<Track> getAllTracks() {
        return mCurrentGame.getAllTracks();
    }

    public synchronized List<City> getAllCities() { return mCurrentGame.getAllCities(); }

    //update all visible player objects
    public synchronized void setLeaderboard(List<PlayerOverview> players) {
        mCurrentGame.setLeaderBoard(players);
        changed();
    }

    //get all visible player objects
    public synchronized List<PlayerOverview> getLeaderboard() {
        return mCurrentGame.getLeaderBoard();
    }


    public synchronized void replaceBankTrainCard(TrainCard card, int pos) {
        mBank.replaceAvailableTrainCard(card, pos);
        changed();
    }

    public synchronized List<TrainCard> getAllBankTrainCards() {
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

    public synchronized Command getPreviousCommand() {
        List<Command> commands = mCommandMgr.getCommandList();
        if(commands.size() == 0){
            return null;
        }
        return commands.get(commands.size() - 1);
    }

    public synchronized int getLastCommandNumber() {
        int size = 0;
        if(mCommandMgr.getCommandList().size() > 0){
            size = mCommandMgr.getCommandList().get(mCommandMgr.getCommandList().size()-1).getCommandNumber();
        }
        return size;
    }

    public synchronized int getNextCommandNumber() {
        return mCommandMgr.getCommandList().size();
    }

    public synchronized boolean isMyTurn() {
        return mCurrentGame.isMyTurn(mCommandMgr);
    }

    /**
     *
     * @return the playerNumber of the player whose turn it is
     */
    public synchronized int getCurrentTurnPlayer() {
        return mCurrentGame.getCurrentTurnPlayer(mCommandMgr);
    }

    /**
     *
     * @return the playerNumber of the player using this device
     */
    public synchronized int getMyPlayerNumber() {
        return mCurrentGame.getMyPlayerNumber();
    }

    public synchronized Hand getHand() {
        return mUser.getHand();
    }

    public synchronized boolean shouldInitializeHand() {
        List<Command> commands = mCommandMgr.getCommandList();
        if(commands.size() > getCurrentGame().getLeaderBoard().size() * 2){
            return false;
        }
        int myNumber = getMyPlayerNumber();
        for (Command command: commands){
            //What if the user draws DestCards later in the game?
            if (command.getPlayerNumber() == myNumber && command instanceof ReturnDestCardsCommand){
                return false;
            }
        }
        return true;
    }

    public synchronized void addCommands(List<Command> newCommands) {
        if (newCommands.size() > 0) {
            mCommandMgr.addCommands(newCommands);
            changed();
        }
    }

    //END COMMAND MANAGER CODE

    /**
     *
     * initializing code
     */

    public synchronized void setCities(List<City> cities) {
        mCurrentGame.setCities(cities);
        changed();
    }

    public synchronized void setTracks(List<Track> tracks) {
        mCurrentGame.setTracks(tracks);
        changed();
    }

    public synchronized void setBankCards(TrainCard[] trainCards) {
        mBank = new Bank(trainCards);
        changed();
    }

    //END INITIALIZING CODE

}
