package goldenhammer.ticket_to_ride_client.ui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

public class MyGamesPresenter extends GameSelectionPresenter implements Observer {
    private GameSelectorActivity owner;

    public MyGamesPresenter(GameSelectorActivity activity){ //TODO Include GameSelectionActivity in constructor
        owner = activity;
    }

    public void getMyGames(){
        ServerProxy.SINGLETON.getPlayerGames(ClientModelFacade.SINGLETON.getUser().getUsername());
    }

    public void createGame(String name){
        try{
            GameName g = new GameName(name);
            ServerProxy.SINGLETON.createGame(g.getString());
        }catch(IOException e){

            owner.toastMessage(e.getMessage());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        owner.setMyGames(ClientModelFacade.SINGLETON.getMyGames());
    }
}
