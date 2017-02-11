package goldenhammer.ticket_to_ride_client.ui;

import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by McKean on 2/6/2017.
 */

public class AvailableGamesPresenter extends GameSelectionPresenter implements Observer{
    private GameSelectorActivity owner;
    public AvailableGamesPresenter(GameSelectorActivity activity){
        owner = activity;
    }

    public void getAvailableGames(){
        ServerProxy.SINGLETON.getAllGames();
    }

    @Override
    public void update(Observable o, Object arg) {
        owner.setAvailableGames(ClientModelFacade.SINGLETON.getAvailableGames());
    }
}
