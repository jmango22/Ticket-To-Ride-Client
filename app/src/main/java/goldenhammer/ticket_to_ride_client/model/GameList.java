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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(!(obj instanceof GameList)) {
            return false;
        }
        else if(((GameList) obj).getAllGames().size() != gameList.size()) {
            return false;
        }
        else {
            for (int i = 0; i < gameList.size(); i++) {
                if (!(gameList.get(i).getName().equals(((GameList) obj).getAllGames().get(i).getName()))) {
                    return false;
                }
                if (gameList.get(i).getPlayers().size() != ((GameList) obj).getAllGames().get(i).getPlayers().size()) {
                    return false;
                }
                for (int j = 0; j < gameList.get(i).getPlayers().size(); j++) {
                    if (!(gameList.get(i).getPlayers().get(j).equals(((GameList) obj).getAllGames().get(i).getPlayers().get(j)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
