package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Hand;

/**
 * Created by McKean on 3/3/2017.
 */

public class InitializeHandCommand extends Command {
    private Hand hand;

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public InitializeHandCommand(int commandNumber) {
        super(commandNumber);
    }

    @Override
    public void execute() {
        if(ClientModelFacade.SINGLETON.getMyPlayerNumber() == getCommandNumber()) {
            ClientModelFacade model = ClientModelFacade.SINGLETON;
            model.setHand(hand);
        }
    }
}
