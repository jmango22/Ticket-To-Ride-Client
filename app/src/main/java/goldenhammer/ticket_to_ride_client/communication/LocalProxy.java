package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

class LocalProxy implements IProxy{
    public static final LocalProxy SINGLETON = new LocalProxy();

    private LocalProxy(){}

    @Override
    public void setURL(String serverHost, String serverPort) {    }

    @Override
    public boolean login(String username, String password) {
        return true;
    }

    @Override
    public boolean register(String username, String password) {
        return true;
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
