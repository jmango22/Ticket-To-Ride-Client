package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

public interface IProxy {

    void setURL(String serverHost, String serverPort);

    boolean login(String username, String password);

    boolean register(String username, String password);

    List<String> getPlayerGames(String username);

    List<String> getAllGames();

    boolean createGame(String gameName);

    boolean joinGame(String gameName);

    void playGame(String gameName);

    boolean leaveGame(String gameName);
}
