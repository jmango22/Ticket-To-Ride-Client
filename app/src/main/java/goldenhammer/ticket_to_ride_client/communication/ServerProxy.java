package goldenhammer.ticket_to_ride_client.communication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public class ServerProxy implements IProxy {
    public static final ServerProxy SINGLETON = new ServerProxy();
    private ClientCommunicator communicator;

    private ServerProxy(){}

    @Override
    public void setURL(String serverHost, String serverPort) {
        communicator = new ClientCommunicator(serverHost, serverPort);
    }

    @Override
    public boolean login(Username username, Password password) {
        try {
            JSONObject body = new JSONObject();
            body.put("username", username.getUsername());
            body.put("password", password.getPassword());
            String url = "/login";
            communicator.post(url, body, null);
            return false;
        }catch(JSONException e){
            return false;
        }
    }

    @Override
    public boolean register(Username username, Password password) {
        try{
            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("password", password);
            String url = "/register";
            communicator.post(url, body, null);
            return false;
        }catch(JSONException e) {
            return false;
        }
    }

    @Override
    public boolean createGame(String gameName) {
        Map<String, String> header = new HashMap<>();
        return false;
    }

    @Override
    public boolean joinGame(String gameName) {
        return false;
    }

    @Override
    public boolean leaveGame(String gameName) {
        return false;
    }

    @Override
    public List<String> getPlayerGames(Username username) {
        String url = "/listofgames?" + username;
        return null;
    }

    @Override
    public List<String> getAllGames() {
        String url = "/listofgames";
        return null;
    }

    @Override
    public void playGame(String gameName) {

    }

}
