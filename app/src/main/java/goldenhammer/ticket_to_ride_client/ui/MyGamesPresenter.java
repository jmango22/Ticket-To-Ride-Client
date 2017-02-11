package goldenhammer.ticket_to_ride_client.ui;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

public class MyGamesPresenter extends GameSelectionPresenter implements Observer {
    private GameSelectorActivity owner;
    private IProxy proxy;

    public MyGamesPresenter(GameSelectorActivity activity){ //TODO Include GameSelectionActivity in constructor
        owner = activity;
        proxy = ServerProxy.SINGLETON;
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

    public void joinGame(String gameName){
        try {
            proxy.joinGame(new GameName(gameName));
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    public void leaveGame(String gameName){
        try {
            proxy.leaveGame(new GameName(gameName));
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    public void playGame(String gameName){
        try {
            proxy.playGame(new GameName(gameName));
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }
}
