package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by seanjib on 3/4/2017.
 */
public class LastTurnCommand extends BaseCommand {
    public LastTurnCommand() {
        setName("LastTurn");
    }

    public LastTurnCommand(int commandNumber){
        setName("LastTurn");
        setCommandNumber(commandNumber);
    }

    @Override
    public void execute() {
        ClientModelFacade.SINGLETON.setLastRound(true);
    }

}
