package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jon on 3/2/17.
 */

public class DrawnDestCards {
    private List<DestCard> cards;

    public DrawnDestCards(List<DestCard> cards) {
        this.cards = cards;
    }

    public void discardDrawnDestCards(List<DestCard> discardedCards) {
        Iterator<DestCard> i = cards.iterator();
        int remainder = discardedCards.size();

        while((i.hasNext()) && (remainder > 0)) {
            DestCard card = i.next();
            for(DestCard discardedCard: discardedCards) {
                if(card.equals(discardedCard)) {
                    remainder = remainder - 1;
                    i.remove();
                }
            }
        }
    }

    public List<DestCard> getRemainingDestCards() {
        List<DestCard> remainingCards = this.cards;
        this.cards = new ArrayList<>();
        return remainingCards;
    }
}
