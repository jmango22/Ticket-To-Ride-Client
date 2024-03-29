package goldenhammer.ticket_to_ride_client.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jon on 2/25/17.
 */

public class ClientModelFacadeTest {
    private ClientModelFacade model;
    final TrainCard card1 = new TrainCard(Color.ORANGE);
    final TrainCard card2 = new TrainCard(Color.BLACK);
    final TrainCard card3 = new TrainCard(Color.BLUE);

    //Orange and Black
    List<TrainCard> trainCards1 = new ArrayList<>(Arrays.asList(card1, card2));
    //Blue
    List<TrainCard> trainCards2 = new ArrayList<>(Arrays.asList(card3));
    //All of them
    List<TrainCard> trainCards3 = new ArrayList<>(Arrays.asList(card1, card2, card3));

    final DestCard dest1 = new DestCard(new City(null, "place1"), new City(null, "place2"), 1);
    final DestCard dest2 = new DestCard(new City(null, "place3"), new City(null, "place4"), 2);
    final DestCard dest3 = new DestCard(new City(null, "place5"), new City(null, "place6"), 3);

    //List of Three Dest Cards
    List<DestCard> loanCards1 = new ArrayList<>(Arrays.asList(dest1, dest2, dest3));
    //List of Two Dest Cards
    List<DestCard> loanCards2 = new ArrayList<>(Arrays.asList(dest1, dest2));
    //List of One Dest Cards
    List<DestCard> loanCards3 = new ArrayList<>(Arrays.asList(dest3));


    @Before
    public void setUp() throws Exception {
        //set user
        model = ClientModelFacade.SINGLETON;
        Player jonny = new Player(new Username("jonny"), new Password("hithere"));
        model.setUser(jonny);

        //set bank cards
        TrainCard[] cards = new TrainCard[5];
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card2;
        cards[3] = card3;
        cards[4] = card1;

        model.setBankCards(cards);

        //set Tracks on the Map
        City place1 = new City(null, "San Diego");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void drawTrainCardsTest() throws Exception {
        System.out.println("Testing adding Train Cards to the Player hand....");
        model.drawTrainCards(trainCards1);

        List<TrainCard> trainCard1Real = model.getUserTrainCards();

        for(int i=0; i<trainCards1.size(); i++) {
            //System.out.println(trainCards1.get(i).toString() + " = " + trainCard1Real.get(i).toString());
            assertEquals(trainCards1.get(i), trainCard1Real.get(i));
        }

        model.drawTrainCards(trainCards2);

        List<TrainCard> trainCard2Expected = trainCards3;
        List<TrainCard> trainCard2Real = model.getUserTrainCards();

        for(int i=0; i<trainCard2Real.size(); i++) {
            //System.out.println(trainCard2Expected.get(i).toString() + " = " + trainCard2Real.get(i).toString());
            assertEquals(trainCard2Expected.get(i), trainCard2Real.get(i));
        }

        model.drawTrainCards(trainCards3);
        assertEquals(model.getUserTrainCards().size(), 6);

        model.drawTrainCards(trainCards3);
        assertEquals(model.getUserTrainCards().size(), 9);

        model.drawTrainCards(trainCards3);
        assertEquals(model.getUserTrainCards().size(), 12);

        model.drawTrainCards(trainCards3);
        assertEquals(model.getUserTrainCards().size(), 15);

    }

    @Test
    public void removeTrainCardsTest() throws Exception {
        System.out.println("Testing removing cards form the Player Hand...");

        model.drawTrainCards(trainCards2);
        model.removeTrainCards(trainCards2);

        List<TrainCard> result1 = model.getUserTrainCards();
        assertEquals(result1.size(), 0);

        model.drawTrainCards(trainCards3);
        model.removeTrainCards(trainCards2);

        List<TrainCard> result2 = model.getUserTrainCards();

        for(int i=0; i<result2.size(); i++) {
            //System.out.println(result2.get(i).toString()+ " = "+trainCards1.get(i).toString());
            assertEquals(result2.get(i), trainCards1.get(i));
        }

        model.removeTrainCards(trainCards1);
        assertEquals(model.getUserTrainCards().size(), 0);

        model.removeTrainCards(trainCards1);
        assertEquals(model.getUserTrainCards().size(), 0);
        model.removeTrainCards(trainCards1);
        assertEquals(model.getUserTrainCards().size(), 0);
        model.removeTrainCards(trainCards1);
        assertEquals(model.getUserTrainCards().size(), 0);
    }

    @Test
    public void takeBankCardTest() throws Exception {
        System.out.println("Testing user taking a bank card...");

        assertEquals(model.getUserTrainCards().size(), 0);

        model.takeBankCard(0);
        assertEquals(model.getUserTrainCards().size(), 1);
        assertEquals(model.getUserTrainCards().get(0), card1);

        assertEquals(model.getAllBankTrainCards().get(0), null);

        model.takeBankCard(1);
        model.takeBankCard(3);

        List<TrainCard> result = model.getUserTrainCards();
        for(int i=0; i<trainCards3.size(); i++) {
            assertEquals(result.get(i), trainCards3.get(i));
        }

        model.takeBankCard(0);
        assertEquals(model.getUserTrainCards().size(), 3);
        model.takeBankCard(1);
        assertEquals(model.getUserTrainCards().size(), 3);
        model.takeBankCard(3);
        assertEquals(model.getUserTrainCards().size(), 3);

        model.takeBankCard(2);
        assertEquals(model.getUserTrainCards().size(), 4);
        model.takeBankCard(4);
        assertEquals(model.getUserTrainCards().size(), 5);
    }

    @Test
    public void setDrawnDestCardsTest() throws Exception {
        System.out.println("Testing adding cards to the DrawnDestCard...");

        assertEquals(model.getUserDestCards().size(), 0);

        model.setDrawnDestCards(loanCards1);

        model.moveDrawnDestCardsToHand(loanCards3);

        assertEquals(model.getUserDestCards().size(), 2);

        List<DestCard> result1 = model.getUserDestCards();

        for(int i = 0; i<result1.size(); i++) {
            assertEquals(result1.get(i), loanCards2.get(i));
        }

        model.moveDrawnDestCardsToHand(new ArrayList<DestCard>());

        assertEquals(model.getUserDestCards().size(), 2);

        model.setDrawnDestCards(loanCards3);

        model.moveDrawnDestCardsToHand(new ArrayList<DestCard>());

        assertEquals(model.getUserDestCards().size(), 3);

        model.moveDrawnDestCardsToHand(new ArrayList<DestCard>());
        assertEquals(model.getUserDestCards().size(), 3);
    }

    @Test
    public void claimTrackTest() throws Exception {
        System.out.println("Track Claiming Tests are not created yet...");
    }
}
