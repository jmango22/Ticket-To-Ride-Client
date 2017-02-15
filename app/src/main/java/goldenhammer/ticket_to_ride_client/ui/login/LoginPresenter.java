package goldenhammer.ticket_to_ride_client.ui.login;

import android.support.v4.util.Pair;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

/**
 * Created by McKean on 2/3/2017.
 */

public class LoginPresenter implements Observer, ILoginPresenter {
    private LoginActivity owner;
    private String portNum;
    private String hostNum;
    private IProxy proxy;

    public LoginPresenter(LoginActivity loginActivity){
        owner = loginActivity;
        ClientModelFacade.SINGLETON.addObserver(this);
        proxy = ServerProxy.SINGLETON;
    }
    @Override
    public void update(Observable o, Object arg) {
        if (ClientModelFacade.SINGLETON.getUser() != null){
            owner.onLogin(ClientModelFacade.SINGLETON.getUser().getUsername().getString());
        }
    }
    public String getPortNum() {
        return portNum;
    }

    public void setPortNum(String portNum) {
        this.portNum = portNum;
    }

    public String getHostNum() {
        return hostNum;
    }

    public void setHostNum(String hostNum) {
        this.hostNum = hostNum;
    }

    @Override
    public void sendLogin(String username, String password, String host, String port) {
        Pair<Username,Password> credentials = check(username, password);
        if (credentials != null){
            String results = proxy.login(credentials.first, credentials.second, host, port);
                owner.toastMessage(results);

        }
    }

    private Pair<Username,Password> check(String username, String password){
        try{
            Username u = new Username(username);
            Password p = new Password(password);
            return new Pair<>(u,p);
        }catch(IOException e){
            String error = e.getMessage();
            owner.toastMessage(error);
        }
        return null;
    }
    @Override
    public void sendRegistration(String username, String password, String host, String port) {
        Pair<Username, Password> credentials = check(username,password);
        if (credentials != null){
            String results = proxy.login(credentials.first, credentials.second, host, port);
            owner.toastMessage(results);
        }
    }



}