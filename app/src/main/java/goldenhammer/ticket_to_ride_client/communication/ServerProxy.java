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
    private Poller poller;
    private ServerProxy(){}
    private String mUsername;

    private void setURL(String serverHost, String serverPort) {
        communicator = new ClientCommunicator(serverHost, serverPort);
    }

    public String getUsername(){
        return mUsername;
    }

    @Override
    public String login(Username username, Password password, String serverHost, String serverPort) {
        try {
            mUsername = username.getString();
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/login";
            String resultMessage = communicator.post(url,body, null);
            if(resultMessage.equals("Success!")){
                if(!communicator.setAuthorizationToken()){
                    return "Token ERROR!";
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
            mUsername = username.getString();
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/register";
            String resultMessage = communicator.post(url,body, null);
            if(resultMessage.equals("Success!")){
               if(!communicator.setAuthorizationToken()){
                   return "Token ERROR";
               }
            }
            return resultMessage;
        }catch(JSONException e) {
            return "ERROR";
        }
    }

    @Override
    public String createGame(GameName gameName) {
        String url = "/creategame";
        String results="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",gameName.getString());
            results = communicator.post(url,jsonObject,gameName.getString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
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
    public String getPlayerGames() {
        String url = "/listofgames?username=" + mUsername;
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

    public void startGameListPolling(){
        if (poller == null){
            poller = new Poller();
        }
    }

    public void stopGameListPolling(){
        poller = null;
    }
}
