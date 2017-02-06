package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public interface IProxy {

    void setURL(String serverHost, String serverPort);

    boolean login(Username username, Password password);

    boolean register(String username, String password);

    List<String> getPlayerGames(String username);

    List<String> getAllGames();

    boolean createGame(String gameName);

    boolean joinGame(String gameName);

    void playGame(String gameName);

    boolean leaveGame(String gameName);
}
