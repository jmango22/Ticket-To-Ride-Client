package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;

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
    private ChatMessages messages;
    private boolean lastRound;
    private State state;
    public  static final  ClientModelFacade SINGLETON = new ClientModelFacade();

    private ClientModelFacade(){
    }

    public synchronized void setLastRound(boolean isLastRound){
        lastRound = isLastRound;
    }

    public synchronized boolean  getLastRound(){
        return lastRound;
    }

    public synchronized ArrayList<EndResult> getEndResults(){
        return mCurrentGame.getEndResults();
    }

    public void setState(State state){
        this.state = state;
        changed();
    }

    public State getState(){
        return state;
    }

    public synchronized void removePieces(int playerNumber, int pieces){
       int currentPieces =  getLeaderboard().get(playerNumber).getNumPieces();
        getLeaderboard().get(playerNumber).setPieces(currentPieces-pieces);
        changed();
    }

    public synchronized void addPoints(int playernumber, int points){
        int myPoints = getLeaderboard().get(playernumber).getPoints();
        getLeaderboard().get(playernumber).setPoints(points+myPoints);
        changed();
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
        if(getCurrentGame() == null) {
            messages = new ChatMessages(new ArrayList<Message>());
            mCommandMgr = new CommandManager();
            lastRound = false;
            this.mCurrentGame = mCurrentGame;
            mCurrentGame.updatePoints();
            offsetTracks();
            mBank = new Bank(mCurrentGame.getBankCards());
            changed();
        }
    }
    public synchronized void clearCurrentGame(){
        mCurrentGame = null;
    }

    public synchronized void offsetTracks(){
        int shiftScale = 16;
        for (Track t : mCurrentGame.getAllTracks()){
            t.setLocation1(t.getCity1().getLocation());
            t.setLocation2(t.getCity2().getLocation());
            if (t.getSecondTrack()){
                PointF normal = getNormal(t.getLocation1(),t.getLocation2());
                t.getLocation1().offset(shiftScale*normal.x,shiftScale*normal.y);
                t.getLocation2().offset(shiftScale*normal.x,shiftScale*normal.y);
            }
        }
    }

    public PointF getNormal(PointF point1, PointF point2){
        float dy = point2.y - point1.y;
        float dx = point2.x - point1.x;
        PointF normal =new PointF(-dy,dx);
        float length = normal.length();
        normal.set(normal.x/length, normal.y/length);
        return normal;
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

    public synchronized TrainCard[] getAllBankTrainCardsArray() {
        if (mBank == null){
            return null;
        }
        TrainCard[] bankCards = mBank.getAvailableTrainCards();
        List<TrainCard> cards = new ArrayList<>();
        for(int i = 0; i<bankCards.length; i++) {
            cards.add(bankCards[i]);
        }
        return bankCards;
    }


    //END PRESENTER CODE

    /**
     * Command Manager Code
     */

    public synchronized BaseCommand getPreviousCommand() {
        List<BaseCommand> commands = mCommandMgr.getCommandList();
        if(commands.size() == 0){
            return null;
        }
        return commands.get(commands.size() - 1);
    }

    public synchronized int getLastCommandNumber() {
        int size = -1;
        if(mCommandMgr.getCommandList().size() > 0){
            size = mCommandMgr.getCommandList().get(mCommandMgr.getCommandList().size()-1).getCommandNumber() + 1;
        }
        return size;
    }

    public synchronized int getNextCommandNumber() {
        return mCommandMgr.getCommandList().size();
    }

    public synchronized boolean isMyTurn() {
        return mCurrentGame.isMyTurn();
    }

    /**
     *
     * @return the playerNumber of the player whose turn it is
     */
    public synchronized int getCurrentTurnPlayer() {
        return mCurrentGame.getCurrentTurnPlayer();
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

    public synchronized void setHand(Hand hand){
        mUser.setHand(hand);
    }

    public synchronized boolean shouldInitializeHand() {
        List<BaseCommand> commands = mCommandMgr.getCommandList();
        if(commands.size() > getCurrentGame().getLeaderBoard().size() * 2){
            return false;
        }
        int myNumber = getMyPlayerNumber();
        for (BaseCommand command: commands){
            //What if the user draws DestCards later in the game?
            if (command.getPlayerNumber() == myNumber && command instanceof ReturnDestCardsCommand){
                return false;
            }
        }
        return true;
    }

    public synchronized void addCommands(List<BaseCommand> newCommands) {
        if (newCommands.size() > 0) {
            mCommandMgr.addCommands(newCommands);
            changed();
        }
    }

    public synchronized void setMessages(ChatMessages c){
        this.messages = c;
        changed();
    }

    public void addMessage(String s){
        messages.getMessages().add(new Message("randomPlayer",s));
    }

    public ChatMessages getMessages(){
        return this.messages;
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

    public synchronized void removeTrainCards(ArrayList<TrainCard> cards){
        mUser.getHand().removeTrainCards(cards);
        changed();
    }

    public synchronized void changeTrackOwner(Track track, int playerNumber){
        List<Track> allTracks = getAllTracks();
        for(Track i: allTracks){
            if(i.getRoute_number() == track.getRoute_number()){
                i.setOwner(playerNumber);
            }
        }
        changed();
    }

    public synchronized void setDrawnDestCards(ArrayList<DestCard> cards){
        DrawnDestCards tempCards = new DrawnDestCards(cards);
        mUser.getHand().setDrawnDestinationCards(tempCards);
        changed();
    }

    public synchronized void addTrainCard(TrainCard card){
        ArrayList cards = new ArrayList<TrainCard>();
        cards.add(card);
        mUser.getHand().addTrainCards(cards);
        changed();
    }

    public synchronized void setEndGameResults(ArrayList<EndResult> results){
        mCurrentGame.setEndResults(results);
        changed();
    }

    public String getGameName(){
        if(getCurrentGame() == null)
            return null;
        return getCurrentGame().getGameName().getString();
    }

    public synchronized boolean isEndGame(){
        return mCurrentGame.getEndGame();
    }
    //END INITIALIZING CODE

}
