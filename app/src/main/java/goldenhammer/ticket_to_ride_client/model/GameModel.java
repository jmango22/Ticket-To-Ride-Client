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

    public GameModel() {
        players = new ArrayList<>();
        destinationDeck = new ArrayList<>();
        trainCardDeck = new ArrayList<>();
        map = new Map();
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

    public List<PlayerOverview> getPlayerHands() {
        return players;
    }

    public void setPlayerHands(List<PlayerOverview> players) {
        this.players = players;
    }
}
