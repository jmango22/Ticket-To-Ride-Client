package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by McKean on 2/3/2017.
 */

public class GameList {
    private List<GameListItem> gameList;

    public GameList(ArrayList<GameListItem> games){
        this.gameList = games;
    }

    public List<GameListItem> getAllGames() {
        return gameList;
    }

    public void setGames(ArrayList<GameListItem> games) {
        this.gameList = games;
    }

    public void addGame(GameListItem game){
        gameList.add(game);
    }

    public GameListItem getGame(int index){
        return gameList.get(index);
    }

}
