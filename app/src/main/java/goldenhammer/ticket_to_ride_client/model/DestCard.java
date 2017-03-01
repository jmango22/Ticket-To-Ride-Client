package goldenhammer.ticket_to_ride_client.model;

/**
 * Created by jon on 2/22/17.
 */

public class DestCard {
    private City city1;
    private City city2;
    private int pointsWorth;

    public DestCard() {
        city1 = new City();
        city2 = new City();
        pointsWorth = 0;
    }

    public DestCard(City city1, City city2, int pointsWorth) {
        this.city1 = city1;
        this.city2 = city2;
        this.pointsWorth = pointsWorth;
    }

    public Boolean isComplete() {
        return false;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public int getPointsWorth() {
        return pointsWorth;
    }

    public void setPointsWorth(int pointsWorth) {
        this.pointsWorth = pointsWorth;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(!(obj instanceof DestCard)) {
            return false;
        }
        else {
            if(((DestCard) obj).getCity1() == city1) {
                if(((DestCard) obj).getCity2() == city2) {
                   if(((DestCard) obj).getPointsWorth() == pointsWorth) {
                       return true;
                   }
                }
            }
            return false;
        }
    }
}
