package goldenhammer.ticket_to_ride_client.model;

import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.IPresenter;

/**
 * Created by McKean on 2/3/2017.
 */

public class LoginPresenter implements Observer, IPresenter {

    public LoginPresenter(){
        ClientModelFacade.SINGLETON.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {

    }
}
