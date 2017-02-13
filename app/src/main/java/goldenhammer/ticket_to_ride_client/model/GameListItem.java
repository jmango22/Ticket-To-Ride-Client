package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameListItem {
    private String name;
    private boolean started;
    private ArrayList<String> players;

    public GameListItem(String name, boolean started, ArrayList<String> players){
        this.name = name;
        this.started = started;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
