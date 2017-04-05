package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by McKean on 4/5/2017.
 */

public class BrokenGameCommand extends BaseCommand {
    @Override
    public void execute() {
        setState();
    }


    private void setState(){
        ClientModelFacade.SINGLETON.setState(StateSelector.BrokenGame());
    }
}
