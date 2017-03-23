package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by McKean on 3/3/2017.
 */

public class EndTurnCommand extends Command {
    public EndTurnCommand(int commandNumber) {
        super(commandNumber);
    }

    @Override
    public void execute() {
        ClientModelFacade.SINGLETON.getCurrentGame().setCurrentTurn(ClientModelFacade.SINGLETON.getCurrentTurnPlayer()+1);
    }
}
