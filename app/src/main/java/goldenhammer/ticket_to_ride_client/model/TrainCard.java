package goldenhammer.ticket_to_ride_client.model;

import android.support.annotation.NonNull;

/**
 * Created by jon on 2/22/17.
 */

public class TrainCard implements Comparable {
    private Color color;

    public TrainCard() {
        color = null;
    }

    public TrainCard(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(!(obj instanceof TrainCard)) {
            return false;
        }
        else {
             return (((TrainCard) obj).getColor() == color);
        }
    }

    @Override
    public String toString() {
        switch (color) {
            case WILD:
                return "WILD";
            case RED:
                return "RED";
            case ORANGE:
                return "ORANGE";
            case YELLOW:
                return "YELLOW";
            case GREEN:
                return "GREEN";
            case BLUE:
                return "BLUE";
            case PURPLE:
                return "PURPLE";
            case BLACK:
                return "BLACK";
            case WHITE:
                return "WHITE";
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof TrainCard){
            TrainCard c = (TrainCard)o;
            return this.toString().compareTo(c.toString());
           }
        return -1;
    }
}
