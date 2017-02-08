package goldenhammer.ticket_to_ride_client.ui;

import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by McKean on 2/6/2017.
 */

public class AvailableGamesPresenter extends GameSelectionPresenter implements Observer{
    private GameSelectionActivity owner;
    public AvailableGamesPresenter(GameSelectionActivity activity){//TODO add GameSelectionActivity to constructor
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
