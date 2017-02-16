package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

class LocalProxy implements IProxy{
    public static final LocalProxy SINGLETON = new LocalProxy();

    private LocalProxy(){}

    @Override
    public String login(Username username, Password password, String serverHost, String serverPor) {
        return "Test";
    }

    @Override
    public String register(Username username, Password password, String serverHost, String serverPor) {
        return "Test";
    }

    @Override
    public String getPlayerGames() {
        return null;
    }



    @Override
    public String getAllGames() {
        return "Test";
    }

    @Override
    public String createGame(GameName gameName) {
        return "Test";
    }

    @Override
    public String joinGame(GameName gameName) {
        return "Test";
    }

    @Override
    public String playGame(GameName gameName) { return "Test";   }

    @Override
    public String leaveGame(GameName gameName) {
        return "Test";
    }
}
