package goldenhammer.ticket_to_ride_client.model.commands;


/**
 * Created by jon on 3/22/17.
 */

public class EndGameCommand extends BaseCommand {
    int winner;
   // List<EndResult> playersResults;

    public EndGameCommand(String gameName) {
        setName("EndGame");
        setGameName(gameName);
    }

    @Override
    public void execute() {

    }
}