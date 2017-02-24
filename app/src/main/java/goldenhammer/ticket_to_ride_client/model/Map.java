package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 2/22/17.
 */

public class Map {
    private List<Track> tracks;
    private List<City> cities;

    public Map() {
        tracks = new ArrayList<>();
        cities = new ArrayList<>();
    }

    public Map(List<Track> tracks, List<City> cities) {
        this.tracks = tracks;
        this.cities = cities;
    }

    //Checks the track to see if an owner exists or is -1
    public Boolean isTrackAvailable(Track track) {
        return false;
    }

    //Claims the track for the player
    public Boolean claimTrack(Track track, List<TrainCard> neededCards, int player) {
        return false;
    }
}
