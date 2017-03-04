package goldenhammer.ticket_to_ride_client.ui.login;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.Callback;
import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.Results;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

public class AvailableGamesPresenter implements Observer, IGameSelectorPresenter{
    private GameSelectorActivity owner;
    private IProxy proxy;


    public AvailableGamesPresenter(GameSelectorActivity activity){
        ClientModelFacade.SINGLETON.addObserver(this);
        owner = activity;
        proxy = ServerProxy.SINGLETON;
    }

    public void getAvailableGames(){
        ServerProxy.SINGLETON.getAllGames();
    }

    @Override
    public void update(Observable o, Object arg) {
        owner.setAvailableGameList(ClientModelFacade.SINGLETON.getAvailableGames().getAllGames());

    }

    public void joinGame(String gameName){
        try {
            proxy.joinGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(res.getBody());
                }
            });
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());        }
    }

    public void leaveGame(String gameName){
        try {
            proxy.leaveGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(res.getBody());
                }
            });
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    public void playGame(String gameName){
        try {
            proxy.playGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(res.getBody());
                }
            });
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }
}
