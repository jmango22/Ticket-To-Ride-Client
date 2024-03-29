package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by seanjib on 3/4/2017.
 */
public class InitializeHandCommand extends BaseCommand {
    private Hand hand;

    public InitializeHandCommand() {
        setName("InitializeHand");
    }

    public InitializeHandCommand(int commandNumber) {
        setName("InitializeHand");
        setCommandNumber(commandNumber);
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void execute() {
        if(ClientModelFacade.SINGLETON.getMyPlayerNumber() == getPlayerNumber()) {
            ClientModelFacade model = ClientModelFacade.SINGLETON;
            model.setHand(hand);
            setState();
        }
    }

    private void setState(){
        ClientModelFacade.SINGLETON.setState(StateSelector.InitializeHand());
    }
}
