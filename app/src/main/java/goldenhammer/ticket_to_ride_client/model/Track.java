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
        owner = -1;
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

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void updateLocation() {
        city1.updateLocation();
        city2.updateLocation();
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean getSecondTrack() {
        return secondTrack;
    }

    public void setSecondTrack(Boolean secondTrack) {
        this.secondTrack = secondTrack;
    }

    public void setLocation1(PointF location1) {
        this.location1 = location1;
    }

    public void setLocation2(PointF location2) {
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

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
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
                        if(((Track) obj).getCity1() == city1) {
                            if(((Track) obj).getCity2() == city2) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }
    public String nametoString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Track from ");
        sb.append(city1.getName() + " to " + city2.getName());
        return sb.toString();
    }

    public String infotoString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Length: ");
        sb.append(length);
        sb.append(" Color: ");
        if(color != null) {
            sb.append(color);
        }else{
            sb.append("Gray");
        }
        return sb.toString();
    }
    public boolean pointByLine(PointF pt, double tolerance) {
        /*double x = location2.x - location1.x;
        double y = location2.y - location1.y;
        double n = (x*x) +(y*y);
        n = Math.sqrt(n);

        double d = ((location1.x* (-y))+(location1.y * x))/n;
        double distance = ((pt.x* (-y))+(pt.y* x))/n;
        distance = distance - d;
        if((distance<= tolerance)&&(distance >= (-tolerance))){
            return true;
        }
        return false;*/
        PointF d = new PointF((float)((location2.x - location1.x)/distance(location1,location2)),
                (float)((location2.y - location1.y)/distance(location1,location2)));

        PointF qTemp = new PointF((pt.x - location1.x), (pt.y - location1.y));
        double temp = dotProd(qTemp,d);
        PointF qTemp2 = new PointF((float)(d.x * temp),(float) (d.y*temp));
        PointF qPrime = new PointF(
                location1.x + qTemp2.x,
                location1.y + qTemp2.y
        );
        return (distance(pt,qPrime) <= tolerance && temp <= distance(location2,location1) && temp >=0);
    }

    private double dotProd(PointF pt1, PointF pt2){
        return ((pt1.x * pt2.x) + (pt1.y * pt2.y));
    }

    public double distance(PointF pt1, PointF pt2){
        double x = pt2.x - pt1.x;
        double y = pt2.y - pt1.y;
        double n = (x*x) +(y*y);
        n = Math.sqrt(n);
        return n;
    }
    /*
		PointF d = new PointF((location2.x - location1.x)/location1.distance(location2),
				(location2.y - location1.y)/location1.distance(location2));

		PointF qTemp = new PointF((pt.x - location1.x), (pt.y - location1.y));
		double temp = dotProd(qTemp,d);
		PointF qTemp2 = new PointF(d.x * temp, d.y*temp);
		PointF qPrime = new PointF(
				location1.x + qTemp2.x,
				location1.y + qTemp2.y
		);
		return (pt.distance(qPrime) <= tolerance && temp <= location2.distance(location1) && temp >=0);*/

    public int getPointValue(){
        switch (length){
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
                return 7;
            case 5:
                return 10;
            case 6:
                return 15;
            default:
                return -1;
        }
    }
}
