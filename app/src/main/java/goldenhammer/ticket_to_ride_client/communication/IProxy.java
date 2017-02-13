package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public interface IProxy {

    boolean login(Username username, Password password, String serverHost, String serverPort);

    boolean register(Username username, Password password, String serverHost, String serverPort);

    boolean getPlayerGames(Username username);

    boolean getAllGames();

    boolean createGame(GameName gameName);

    boolean joinGame(GameName gameName);

    boolean playGame(GameName gameName);

    boolean leaveGame(GameName gameName);
}
