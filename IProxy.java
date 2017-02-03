package edu.byu.cs240.rachel.phase1;

import java.util.List;

/**
 * Created by rache on 2/3/2017.
 */

public interface IProxy {
    boolean login(String username, String password);

    boolean register(String username, String password);

    List<String> getPlayerGames(String username);

    List<String> getAllGames();
}
