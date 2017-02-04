package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

class LocalProxy implements IProxy{
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
