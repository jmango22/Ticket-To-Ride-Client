package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 3/2/17.
 */

public class DrawnDestCards {
    private DestCard[] cards = new DestCard[3];

    public DrawnDestCards(List<DestCard> cards) throws IllegalArgumentException {
        if(cards.size() != 3) {
            throw new IllegalArgumentException();
        } else {
            for(int i=0; i<cards.size(); i++) {
                this.cards[0] = cards.get(0);
            }
        }
    }

    public DrawnDestCards(DestCard[] cards) throws IllegalArgumentException {
        if(cards.length != 3) {
            throw new IllegalArgumentException();
        } else {
            this.cards = cards;
        }
    }

    public DestCard getDestCard(int pos) {
        return cards[pos];
    }

}
