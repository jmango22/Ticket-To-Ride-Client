package goldenhammer.ticket_to_ride_client.communication;

import org.json.JSONException;
import org.json.JSONObject;


import goldenhammer.ticket_to_ride_client.model.GameName;
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
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/login";
            boolean result = communicator.post(url,body, null);
            if(result){
                return communicator.setAuthorizationToken();
            }
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
            boolean result = communicator.post(url,body, null);
            if(result){
                return communicator.setAuthorizationToken();
            }
            return false;
        }catch(JSONException e) {
            return false;
        }
    }

    @Override
    public boolean createGame(GameName gameName) {
        String url = "/creategame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public boolean joinGame(GameName gameName) {
        String url = "/joingame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public boolean leaveGame(GameName gameName) {
        String url = "/leavegame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public boolean getPlayerGames(Username username) {
        String url = "/listofgames?" + username;
        communicator.get(url, null);
        return false;
    }

    @Override
    public boolean getAllGames() {
        String url = "/listofgames";
        communicator.get(url, null);
        return false;
    }

    @Override
    public boolean playGame(GameName gameName) {
        String url = "/playgame";
        communicator.get(url, gameName.getString());
        return false;
    }

}
