package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameModel {
    private List<PlayerOverview> players;
    private List<Color> mBank;
    private Map map;
    private GameName name;
    private int currentTurn;
    private ArrayList<EndResult> results;
    private int checkpointIndex;

    private String state;

    private java.util.Map<String,Hand> hands;
    private boolean lastRound;


    public GameModel() {
        players = new ArrayList<>();
        map = new Map();
        currentTurn = 0;
    }


    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    public List<DestCard> getDestinationCards(String myName) {
        return hands.get(myName).getDestinationCards();
    }

    public List<TrainCard> getTrainCards(String myName) {
        return hands.get(myName).getTrainCards();
    }

    public void addTrainCards(List<TrainCard> drawnCards,String myName) {
        hands.get(myName).addTrainCards(drawnCards);
    }

    public void addBankCard(TrainCard card,String myName) {
        hands.get(myName).addBankCard(card);
    }

    public void removeTrainCards(List<TrainCard> cards,String myName) {
        hands.get(myName).removeTrainCards(cards);
    }

    public void setDrawDestCards(List<DestCard> cards,String myName) {
        hands.get(myName).setDrawnDestinationCards(cards);
    }

    public void moveDrawnDestCards(List<DestCard> discardedCards,String myName) {
        hands.get(myName).moveDrawnDestCardToHand(discardedCards);
    }
    public Hand getHand(String myName){
        return hands.get(myName);
    }

    public void setHand(Hand hand, String myName){
        hands.put(myName,hand);
    }


    public int getCheckpointIndex() {
        return checkpointIndex;
    }

    public void setCheckpointIndex(int checkpointIndex) {
        this.checkpointIndex = checkpointIndex;
    }


    public TrainCard[] getBankCards(){
        TrainCard[] deck = new TrainCard[mBank.size()];
        for(int i = 0; i < mBank.size();i++){
            deck[i] = new TrainCard(mBank.get(i));
        }
        return deck;
    }

    public GameName getGameName(){
        return name;
    }

    public Boolean initGameData() {
        return false;
    }

    public void claimTrack(Track track, int player) {
        map.claimTrack(track, player);
    }

    public List<Track> getAllTracks() {
        return map.getTracks();
    }

    public List<City> getAllCities() { return map.getCities(); }

    public List<PlayerOverview> getLeaderBoard() {
        return players;
    }

    public void setLeaderBoard(List<PlayerOverview> players) {
        this.players = players;
    }

    public void setCities(List<City> cities) {
        map.setCities(cities);
    }

    public void setTracks(List<Track> tracks) {
        map.setTracks(tracks);
    }

    public Map getMap() {
        return map;
    }

    public int getMyPlayerNumber() {
        for(PlayerOverview player: players) {
            if(player.getUsername().equals(ClientModelFacade.SINGLETON.getUser().getUsername().getString())) {
                return player.getPlayer();
            }
        }
        return -1;
    }

    public void updatePoints(){
        for(City city: getAllCities()){
            city.updateLocation();
        }
        for(Track track: getAllTracks()){
            track.updateLocation();
        }
    }

    public boolean isMyTurn() {
        if(getCurrentTurnPlayer() == getMyPlayerNumber()) {
            return true;
        }
        return false;
    }

   /* public int getCurrentTurnPlayer(CommandManager cmdMgr) {
        List<Command> allCommands = cmdMgr.getCommandList();
        //Umm... are we using the number of EndTurn Commands to find out who's turn it is?
        int numberOfEndTurns = 0;
        for(Command command: allCommands) {
            if(command.getName().equals("EndTurn")) {
                numberOfEndTurns++;
            }
        }
        //This is correct if we start with player zero. If we start with player number 1 add 1 to this.
        return (numberOfEndTurns % players.size());
    }*/

    public int getCurrentTurnPlayer(){
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn){
        if((currentTurn < 0)||(currentTurn >= players.size())){
            this.currentTurn = 0;
        }else{
            this.currentTurn = currentTurn;
        }
    }

    public boolean getEndGame(){
        if(results == null){
            return false;
        }else{
            return true;
        }
    }

    public void setEndResults(ArrayList<EndResult> results){
        this.results = results;
        
    }

    public ArrayList<EndResult> getEndResults(){
        return results;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
