package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;

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

    //Claims the track for the player
    public void claimTrack(Track track, int player) {
        for(Track trackey : tracks) {
            if(trackey.equals(track)) {
                trackey.setOwner(player);
            }
        }
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }


}
