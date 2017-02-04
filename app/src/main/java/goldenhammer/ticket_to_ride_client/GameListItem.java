package goldenhammer.ticket_to_ride_client;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameListItem {
    private String id;
    private String name;
    private boolean started;
    private ArrayList<Player> players;

    public GameListItem(String id, String name, boolean started, ArrayList<Player> players){
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
