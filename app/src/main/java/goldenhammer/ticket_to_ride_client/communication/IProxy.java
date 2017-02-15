package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public interface IProxy {

    String login(Username username, Password password, String serverHost, String serverPort);

    String register(Username username, Password password, String serverHost, String serverPort);

    String getPlayerGames(Username username);

    String getAllGames();

    String createGame(GameName gameName);

    String joinGame(GameName gameName);

    String playGame(GameName gameName);

    String leaveGame(GameName gameName);
}
