package goldenhammer.ticket_to_ride_client.model;

/**
 * Created by jon on 2/22/17.
 */

public class TrainCard {
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

}
