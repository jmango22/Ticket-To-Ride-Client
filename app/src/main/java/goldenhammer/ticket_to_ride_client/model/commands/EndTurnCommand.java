package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by seanjib on 3/4/2017.
 */
public class EndTurnCommand extends BaseCommand {
    int previousPlayer;
    int nextPlayer;
    public EndTurnCommand() {
        setName("EndTurn");
    }

    public EndTurnCommand(int commandNumber){
        setName("EndTurn");
        setCommandNumber(commandNumber);
    }

    @Override
    public void execute() {
        ClientModelFacade.SINGLETON.getCurrentGame().setCurrentTurn(nextPlayer);
        if(ClientModelFacade.SINGLETON.getCurrentTurnPlayer() == ClientModelFacade.SINGLETON.getMyPlayerNumber()){
            ClientModelFacade.SINGLETON.setState(StateSelector.MyTurn());
        }else{
            ClientModelFacade.SINGLETON.setState(StateSelector.NotMyTurn());
        }
    }

}
