package goldenhammer.ticket_to_ride_client.ui;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Created by McKean on 2/3/2017.
 */

public interface ILoginPresenter {

    public void sendLogin(String username, String password);

    public void sendRegistration(String username, String password);

    public void setURL(String host, String port);

}
