package goldenhammer.ticket_to_ride_client.communication;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameList;
import goldenhammer.ticket_to_ride_client.model.GameModel;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public class ServerProxy implements IProxy {
    public static final ServerProxy SINGLETON = new ServerProxy();
    private ClientCommunicator communicator;

    private ServerProxy(){}

    private void setURL(String serverHost, String serverPort) {
        communicator = new ClientCommunicator(serverHost, serverPort);
    }

    @Override
    public String login(Username username, Password password, String serverHost, String serverPort) {
        try {
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/login";
            String resultMessage = communicator.post(url,body, null);
            if(resultMessage.equals("Success!")){
                if(!communicator.setAuthorizationToken()){
                    return "ERROR!";
                }
            }
            return resultMessage;
        }catch(JSONException e){
            return "ERROR!";
        }
    }

    @Override
    public String register(Username username, Password password, String serverHost, String serverPort) {
        try{
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/register";
            String resultMessage = communicator.post(url,body, null);
            if(resultMessage.equals("Success!")){
               if(!communicator.setAuthorizationToken()){
                   return "set auth failed";
               }
            }
            return resultMessage;
        }catch(JSONException e) {
            return e.getMessage();
        }
    }

    @Override
    public String createGame(GameName gameName) {
        String url = "/creategame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public String joinGame(GameName gameName) {
        String url = "/joingame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public String leaveGame(GameName gameName) {
        String url = "/leavegame";
        return communicator.post(url, null, gameName.getString());
    }

    @Override
    public String getPlayerGames(Username username) {
        String url = "/listofgames?" + username;
        String returnMessage = communicator.get(url, null);
        if(returnMessage.equals("Success!")){
            ClientModelFacade.SINGLETON.setMyGames(deserializeGames());
        }
        return returnMessage;
    }

    @Override
    public String getAllGames() {
        String url = "/listofgames";
        String returnMessage = communicator.get(url, null);
        if(returnMessage.equals("Success!")){
            ClientModelFacade.SINGLETON.setAvailableGames(deserializeGames());
        }
        return returnMessage;
    }

    @Override
    public String playGame(GameName gameName) {
        String url = "/playgame";
        String returnMessage = communicator.get(url, gameName.getString());
        if (returnMessage.equals("Success!")){
            GameModel game = deserializeGameModel();
            if(game != null) {
                ClientModelFacade.SINGLETON.setCurrentGame(game);
            }
        }
        return returnMessage;
    }


    private GameList deserializeGames(){
        Gson gson = new Gson();
        return gson.fromJson(communicator.getResults(),GameList.class);
    }

    private GameModel deserializeGameModel(){
        Gson gson = new Gson();
        return gson.fromJson(communicator.getResults(), GameModel.class);
    }
}
