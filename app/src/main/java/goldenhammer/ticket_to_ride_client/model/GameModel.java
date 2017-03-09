package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.commands.Command;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameModel {
    private List<PlayerOverview> players;
    private List<DestCard> destinationDeck;
    private List<TrainCard> trainCardDeck;
    private Map map;
    private GameName name;

    public GameModel() {
        players = new ArrayList<>();
        destinationDeck = new ArrayList<>();
        trainCardDeck = new ArrayList<>();
        map = new Map();
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

    public boolean isMyTurn(CommandManager cmdMgr) {
        if(getCurrentTurnPlayer(cmdMgr) == getMyPlayerNumber()) {
            return true;
        }
        return false;
    }

    public int getCurrentTurnPlayer(CommandManager cmdMgr) {
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
    }


}
