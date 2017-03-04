package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

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
}
