package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

public class ServerProxy implements IProxy {
    public static final ServerProxy SINGLETON = new ServerProxy();

    private ServerProxy(){}

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean register(String username, String password) {
        return false;
    }

    @Override
    public List<String> getPlayerGames(String username) {
        return null;
    }

    @Override
    public List<String> getAllGames() {
        return null;
    }
}
