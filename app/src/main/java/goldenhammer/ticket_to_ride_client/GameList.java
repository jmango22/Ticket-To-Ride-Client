package goldenhammer.ticket_to_ride_client;

import java.util.ArrayList;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameList {
    private ArrayList<GameListItem> games;

    public GameList(ArrayList<GameListItem> games){
        this.games = games;
    }

    public ArrayList<GameListItem> getAllGames() {
        return games;
    }

    public void setGames(ArrayList<GameListItem> games) {
        this.games = games;
    }

    public void addGame(GameListItem game){
        games.add(game);
    }

    public GameListItem getGame(int index){
        return games.get(index);
    }

}
