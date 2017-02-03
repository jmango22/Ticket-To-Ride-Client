package goldenhammer.ticket_to_ride_client;

import java.util.List;

/**
 * Created by rachel on 2/3/2017.
 * This class is the interface that every Proxy class must inherit from
 */

public interface IProxy {
    boolean login(String username, String password);

    boolean register(String username, String password);

    List<String> getPlayerGames(String username);

    List<String> getAllGames();
}
