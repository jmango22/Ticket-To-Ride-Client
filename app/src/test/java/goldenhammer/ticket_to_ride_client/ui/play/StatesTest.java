package goldenhammer.ticket_to_ride_client.ui.play;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import goldenhammer.ticket_to_ride_client.ui.play.states.DrawSecondTrainCardsState;
import goldenhammer.ticket_to_ride_client.ui.play.states.InitializeHandState;
import goldenhammer.ticket_to_ride_client.ui.play.states.MyTurnState;
import goldenhammer.ticket_to_ride_client.ui.play.states.NotMyTurnState;
import goldenhammer.ticket_to_ride_client.ui.play.states.ReturnDestCardsState;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by devonkinghorn on 4/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class StatesTest {
    @Mock GamePlayPresenter presenter;

    @Test
    public void testNotMyTurnState() {

        NotMyTurnState state = new NotMyTurnState(presenter);
        state.returnDestCards(null);
        verify(presenter, times(0)).sendReturnDestCardsCommand(null);
        state.layTrack(null);
        verify(presenter, times(0)).sendLayTrackCommand(null);
        state.takeDestCards();
        verify(presenter, times(0)).sendTakeDestCardsCommand();
    }

    @Test
    public void testMyTurnState() {
        MyTurnState state = new MyTurnState(presenter);
        state.returnDestCards(null);
        verify(presenter, times(0)).sendReturnDestCardsCommand(null);
        state.layTrack(null);
        verify(presenter, times(1)).sendLayTrackCommand(null);
        state.takeDestCards();
        verify(presenter, times(1)).sendTakeDestCardsCommand();
        state.takeTrainCards(1);
        verify(presenter, times(1)).sendTakeTrainCardsCommand(1);
    }

    @Test
    public void testReturnDestCardsState() {
        ReturnDestCardsState state = new ReturnDestCardsState(presenter);
        state.returnDestCards(null);
        verify(presenter, times(1)).sendReturnDestCardsCommand(null);
        state.layTrack(null);
        verify(presenter, times(0)).sendLayTrackCommand(null);
        state.takeDestCards();
        verify(presenter, times(0)).sendTakeDestCardsCommand();
        state.takeTrainCards(1);
        verify(presenter, times(0)).sendTakeTrainCardsCommand(1);
    }

    @Test
    public void testDrawSecondTrainCardState() {
        DrawSecondTrainCardsState state = new DrawSecondTrainCardsState(presenter);
        state.returnDestCards(null);
        verify(presenter, times(0)).sendReturnDestCardsCommand(null);
        state.layTrack(null);
        verify(presenter, times(0)).sendLayTrackCommand(null);
        state.takeDestCards();
        verify(presenter, times(0)).sendTakeDestCardsCommand();
        state.takeTrainCards(1);
        verify(presenter, times(1)).sendTakeTrainCardsCommand(1);
    }

    @Test
    public void testInitializeHand() {
        InitializeHandState state = new InitializeHandState(presenter);
        state.returnDestCards(null);
        verify(presenter, times(1)).sendReturnDestCardsCommand(null);
        state.layTrack(null);
        verify(presenter, times(0)).sendLayTrackCommand(null);
        state.takeDestCards();
        verify(presenter, times(0)).sendTakeDestCardsCommand();
        state.takeTrainCards(1);
        verify(presenter, times(0)).sendTakeTrainCardsCommand(1);
    }
}
