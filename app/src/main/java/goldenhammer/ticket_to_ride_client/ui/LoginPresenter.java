package goldenhammer.ticket_to_ride_client.ui;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.communication.ILoginPresenter;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;
import goldenhammer.ticket_to_ride_client.ui.LoginActivity;

/**
 * Created by McKean on 2/3/2017.
 */

public class LoginPresenter implements Observer, ILoginPresenter {
    private LoginActivity owner;

    public LoginPresenter(LoginActivity loginActivity){
        owner = loginActivity;
        ClientModelFacade.SINGLETON.addObserver(this);
    }
    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void sendLogin(String username, String password) {
        Pair<Username,Password> credentials = check(username, password);
        if (credentials != null){
            //TODO proxy.login()
        }
    }

    private Pair<Username,Password> check(String username, String password){
        try{
            Username u = new Username(username);
            Password p = new Password(password);
            return new Pair<>(u,p);
        }catch(IOException e){
            String error = e.getMessage();
            //TODO call LoginActivity's function with error message.
        }
        return null;
    }

    @Override
    public void sendRegistration(String username, String password) {
        Pair<Username, Password> credentials = check(username,password);
        if (credentials != null){
            //ServerProxy.SINGLETON.login()
            //TODO finish working with serverProxy
        }
    }

}
