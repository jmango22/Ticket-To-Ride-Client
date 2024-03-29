package goldenhammer.ticket_to_ride_client.communication;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;
import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;

public interface IProxy {

    void login(Username username, Password password, String serverHost, String serverPort, Callback c);

    void register(Username username, Password password, String serverHost, String serverPort, Callback c);

    void getPlayerGames(Callback c);

    void getAllGames(Callback c);

    void createGame(GameName gameName, Callback c);

    void joinGame(GameName gameName, Callback c);

    void playGame(GameName gameName, Callback c);

    void leaveGame(GameName gameName, Callback c);

    void doCommand(BaseCommand command, Callback c);

    void getCommands(int lastCommandNumber, Callback c);

    void getMessages(Callback c);

    void postMessage(String message, Callback c);

}
