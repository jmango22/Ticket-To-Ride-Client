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

public class AvailableGamesPresenter extends GameSelectionPresenter implements Observer{
    private GameSelectorActivity owner;
    private IProxy proxy;

    public AvailableGamesPresenter(GameSelectorActivity activity){//TODO add GameSelectionActivity to constructor
        owner = activity;
        proxy = ServerProxy.SINGLETON;
    }

    public void getAvailableGames(){
        ServerProxy.SINGLETON.getAllGames();
    }

    @Override
    public void update(Observable o, Object arg) {
        owner.setAvailableGames(ClientModelFacade.SINGLETON.getAvailableGames());
    }

    public void joinGame(String gameName){
        try {
            proxy.joinGame(new GameName(gameName));
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());        }
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
