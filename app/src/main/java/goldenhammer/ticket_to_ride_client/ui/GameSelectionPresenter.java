package goldenhammer.ticket_to_ride_client.ui;

import java.io.IOException;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

public abstract class GameSelectionPresenter {
    private IProxy proxy = ServerProxy.SINGLETON;


}
