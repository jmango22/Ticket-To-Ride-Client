package goldenhammer.ticket_to_ride_client.ui.login;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Created by McKean on 2/3/2017.
 */

public interface ILoginPresenter {

    public void sendLogin(String username, String password, String host, String port);

    public void sendRegistration(String username, String password, String host, String port);

    public void onPause();
    public void onResume();


}
