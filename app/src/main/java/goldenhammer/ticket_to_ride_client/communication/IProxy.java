package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public interface IProxy {

    void login(Username username, Password password, String serverHost, String serverPort);

    void register(Username username, Password password, String serverHost, String serverPort);

    void getPlayerGames();

    void getAllGames();

    void createGame(GameName gameName);

    void joinGame(GameName gameName);

    void playGame(GameName gameName);

    void leaveGame(GameName gameName);
}
