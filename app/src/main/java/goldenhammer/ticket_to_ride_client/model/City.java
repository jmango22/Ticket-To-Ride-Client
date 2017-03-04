package goldenhammer.ticket_to_ride_client.model;

import android.graphics.PointF;

/**
 * Created by jon on 2/22/17.
 */

public class City {
    private PointF location;

    private String name;

    public City() {
        location = new PointF();
        name = "";
    }

    public City(PointF location, String name) {
        this.location = location;
        this.name = name;
    }

    public PointF getLocation() {
        return location;
    }

    public void setLocation(PointF location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        else if (!(obj instanceof City)) {
            return false;
        } else {
            if(((City) obj).getLocation() == this.location) {
                if(((City) obj).getName() == this.name) {
                    return true;
                }
            }
            return false;
        }
    }
}
