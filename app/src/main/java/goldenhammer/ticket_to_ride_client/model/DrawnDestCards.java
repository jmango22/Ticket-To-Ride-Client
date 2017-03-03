package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 3/2/17.
 */

public class DrawnDestCards {
    private DestCard[] cards;

    public DrawnDestCards(List<DestCard> cards) {
        this.cards = new DestCard[cards.size()];
        for(int i=0; i<cards.size(); i++) {
            this.cards[0] = cards.get(0);
        }
    }

    public DrawnDestCards(DestCard[] cards) {
        this.cards = cards;
    }

    public DestCard getDestCard(int pos) {
        return cards[pos];
    }

}
