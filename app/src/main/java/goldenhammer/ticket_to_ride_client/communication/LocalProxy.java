package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

class LocalProxy implements IProxy{
    public static final LocalProxy SINGLETON = new LocalProxy();

    private LocalProxy(){}

    @Override
    public void setURL(String serverHost, String serverPort) {    }

    @Override
    public boolean login(Username username, Password password) {
        return true;
    }

    @Override
    public boolean register(Username username, Password password) {
        return true;
    }

    @Override
    public List<String> getPlayerGames(Username username) {
        return null;
    }

    @Override
    public List<String> getAllGames() {
        return null;
    }

    @Override
    public boolean createGame(GameName gameName) {
        return false;
    }

    @Override
    public boolean joinGame(GameName gameName) {
        return false;
    }

    @Override
    public void playGame(GameName gameName) {

    }

    @Override
    public boolean leaveGame(GameName gameName) {
        return false;
    }
}
