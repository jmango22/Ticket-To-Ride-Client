package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;
/**
 * Created by jon on 2/22/17.
 */

public class Track {
    private City city1;
    private City city2;
    private int length;
    private Color color;
    private int owner;
    private Boolean secondTrack;
    private PointF location1;
    private PointF location2;

    public Track() {
        city1 = new City();
        city2 = new City();
        length = 0;
        color = null;
        owner = 0;
        secondTrack = false;
        location1 = new PointF();
        location2 = new PointF();
    }

    public Track(City city1, City city2, int length, Color color, int owner, Boolean secondTrack, PointF location1, PointF location2) {
        this.city1 = city1;
        this.city2 = city2;
        this.length = length;
        this.color = color;
        this.owner = owner;
        this.secondTrack = secondTrack;
        this.location1 = location1;
        this.location2 = location2;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public PointF getLocation1() {
        return location1;
    }

    public PointF getLocation2() {
        return location2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(!(obj instanceof Track)) {
            return false;
        }
        else {
            if(((Track) obj).getLocation1() == location1) {
                if(((Track) obj).getLocation2() == location2) {
                    if(((Track) obj).getOwner() == owner) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}