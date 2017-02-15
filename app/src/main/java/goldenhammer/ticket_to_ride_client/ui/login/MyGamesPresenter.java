package goldenhammer.ticket_to_ride_client.ui.login;

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

public class MyGamesPresenter implements Observer, IGameSelectorPresenter {
    private GameSelectorActivity owner;
    private IProxy proxy;


    public MyGamesPresenter(GameSelectorActivity activity){
        owner = activity;
        proxy = ServerProxy.SINGLETON;
    }

    public void getMyGames(){
        ServerProxy.SINGLETON.getPlayerGames(ClientModelFacade.SINGLETON.getUser().getUsername());
    }

    public void createGame(String name){
        try{
            GameName g = new GameName(name);
            ServerProxy.SINGLETON.createGame(g);
        }catch(IOException e){

            owner.toastMessage(e.getMessage());
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        owner.setMyGameList(ClientModelFacade.SINGLETON.getMyGames().getAllGames());
    }

    public void joinGame(String gameName){
        try {
            if (!proxy.joinGame(new GameName(gameName))){
                owner.toastMessage("Unable to join game.");
            }
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    public void leaveGame(String gameName){
        try {
            if (!proxy.leaveGame(new GameName(gameName))){
                owner.toastMessage("Unable to leave game.");
            }
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    public void playGame(String gameName){
        try {
            proxy.playGame(new GameName(gameName));
            owner.onPlayGame();
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }
}
