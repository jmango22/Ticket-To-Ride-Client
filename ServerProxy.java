package edu.byu.cs240.rachel.phase1;

import java.util.List;

/**
 * Created by rache on 2/3/2017.
 */

public class ServerProxy implements IProxy {
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
