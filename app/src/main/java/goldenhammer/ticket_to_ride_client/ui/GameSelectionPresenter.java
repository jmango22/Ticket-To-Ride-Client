package goldenhammer.ticket_to_ride_client.ui;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

public abstract class GameSelectionPresenter {
    private IProxy proxy = ServerProxy.SINGLETON;

    public void joinGame(GameName gameName){
        proxy.joinGame(gameName);
    }

    public void leaveGame(GameName gameName){
        proxy.leaveGame(gameName);
    }

    public void playGame(GameName gameName){
        proxy.playGame(gameName);
    }
}
