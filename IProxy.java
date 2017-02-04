package edu.goldenhammer.ticket_to_ride_client;

import java.util.List;

public interface IProxy {
    boolean login(String username, String password);

    boolean register(String username, String password);

    List<String> getPlayerGames(String username);

    List<String> getAllGames();
}
