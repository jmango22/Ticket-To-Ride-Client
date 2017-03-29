package goldenhammer.ticket_to_ride_client.model.commands;


import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.EndResult;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

/**
 * Created by jon on 3/22/17.
 */

public class EndGameCommand extends BaseCommand {
    int winner;
    List<EndResult> playersResults;

    public EndGameCommand(String gameName) {
        setName("EndGame");
        setGameName(gameName);
    }

    @Override
    public void execute() {
        ArrayList<EndResult> results = new ArrayList<>();
        for(EndResult e: playersResults) {
            results.add(e);
        }
        ClientModelFacade.SINGLETON.setEndGameResults(results);
        ClientModelFacade.SINGLETON.setState(StateSelector.EndGame());
    }

    public void setPlayersResults(List<EndResult> results){
        this.playersResults = results;
    }
}