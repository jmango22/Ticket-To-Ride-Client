package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public interface IProxy {

    void setURL(String serverHost, String serverPort);

    boolean login(Username username, Password password);

    boolean register(Username username, Password password);

    List<String> getPlayerGames(Username username);

    List<String> getAllGames();

    boolean createGame(GameName gameName);

    boolean joinGame(GameName gameName);

    void playGame(GameName gameName);

    boolean leaveGame(GameName gameName);
}
