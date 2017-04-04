package goldenhammer.ticket_to_ride_client.communication;

import android.telecom.Call;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameList;
import goldenhammer.ticket_to_ride_client.model.GameModel;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Player;
import goldenhammer.ticket_to_ride_client.model.Username;
import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;

/**
 * JavaDoc created by Rachel on 2/22/2017
 * ServerProxy is responsible for taking information from the Presenters, preparing it for the ClientCommunicator to send to the Server.
 * ServerProxy holds the username of the player and initiates the poller
 * @invariant This class implements IProxy
 * @invariant the ClientCommunicator is communicating with a server with the given endpoints
 * ("/login", "/register", "/creategame", "/joingame", "/leavegame", "/listofgames", and "/playgame")
 * and that the server accepts the information in the format provided
 */
public class ServerProxy implements IProxy {
    public static final ServerProxy SINGLETON = new ServerProxy();
    private ClientCommunicator communicator;
    private GamePoller gamePoller;
    private CommandPoller commandPoller;
    private ServerProxy(){}
    /**
     * Creates the ClientCommunicator member communicator and handles the response
     * @pre The server host and server port are valid strings that connect to a valid running server
     * @pre The parameters are not null
     * @pre This method is called before any other method is called on communicator
     * @post a communicator is initiated with a valid server address. (a connection can now be formed)
     * @param serverHost a string with the address (Usually IP address) where the desired server is running.
     * @param serverPort a string with the port number (4 digit) where the desired server can be found.
     */
    private void setURL(String serverHost, String serverPort) {
        communicator = new ClientCommunicator(serverHost, serverPort);
    }


    /**
     * Sends a login request to the ClientCommunicator and handles the response
     * @pre Username, Password, serverHost, and serverPort all contain valid input (and are not null)
     * @post the user will be logged in or rejected(given if the combination of username and password is valid)
     * @post a message will be returned with the result of the login attempt
     * @param username of type Username, that contains the username of the user
     * @param password of type Password, that contains the password of the user
     * @param serverHost of type String, that contains the address of the server
     * @param serverPort of type String, that contains the port of the server
     * @return a string that indicates whether or not the login was successful or what the error was.
     */
    @Override
    public void login(final Username username, final Password password, String serverHost, String serverPort, final Callback callback) {
        try {
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/login";
            communicator.setUsername(username.getString());
            communicator.post(url, body, new Callback() {
                @Override
                public void run(Results res) {
                    if (res.getResponseCode() == HttpURLConnection.HTTP_OK){
                        try {
                            JSONObject results = new JSONObject(res.getBody());
                            communicator.setAuthorizationToken(results.getString("authorization"));
                            ClientModelFacade.SINGLETON.setUser(new Player(username,password));
                            callback.run(new Results("Success!", HttpURLConnection.HTTP_OK));
                        }catch(JSONException e){

                        }
                    }
                    else{
                        callback.run(res);
                    }
                }
            });
        }catch(JSONException e){
        }
    }

    /**
     * Sends a new request to register to the communicator and handles the response
     * @pre Username, Password, serverHost, and serverPort are all not null
     * @post the user will be registered (if the username doesn't already exist) and logged in
     * @post a message will be returned with the result of the register attempt
     * @param username of type Username, that contains the username of the user
     * @param password of type Password, that contains the password of the user
     * @param serverHost of type String, that contains the address of the server
     * @param serverPort of type String, that contains the port of the server
     * @return a string that indicates whether or not the register was successful and what the error was.
     */
    @Override
    public void register(final Username username, final Password password, String serverHost, String serverPort, final Callback callback) {
        try{
            setURL(serverHost,serverPort);
            JSONObject body = new JSONObject();
            body.put("username", username.getString());
            body.put("password", password.getString());
            String url = "/register";
            communicator.setUsername(username.getString());
            communicator.post(url, body, new Callback() {
                @Override
                public void run(Results res) {
                    if (res.getResponseCode() == HttpURLConnection.HTTP_OK){
                        try {
                            JSONObject results = new JSONObject(res.getBody());
                            communicator.setAuthorizationToken(results.getString("authorization"));
                            ClientModelFacade.SINGLETON.setUser(new Player(username,password));
                            callback.run(new Results("Success!", HttpURLConnection.HTTP_OK));
                        }catch(JSONException e){

                        }
                    }
                    else{
                        callback.run(res);
                    }
                }
            });
        }catch(JSONException e) {
        }
    }

    /**
     * Sends a create Game request to the communicator and handles the response
     * @pre gameName is not null
     * @pre there has been a successful login or register request before the call of this method
     * @post a game is created or not based on the gameName
     * @post a message is return indicating success
     * @param gameName of type GameName, that contains the desired game
     * @return a string message detailing the result of the request
     */
    @Override
    public void createGame(GameName gameName, Callback c){
        String url = "/creategame";
        String results="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",gameName.getString());
            communicator.setGameName(gameName.getString());
            communicator.post(url,jsonObject, c);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends a join Game request to the communicator and handles the response
     * @pre gameName is not null
     * @pre there has been a successful login or register request before the call of this method
     * @post the logged in user is added to a game or not if the server returns that it is not valid
     * @post a message is return indicating success
     * @param gameName of type GameName, that contains the desired game
     * @return a string message detailing the result of the request
     */
    @Override
    public void joinGame(GameName gameName, Callback c) {
        String url = "/joingame";
        communicator.setGameName(gameName.getString());
        communicator.post(url, null, c);
    }


    /**
     * Sends a leave Game request to the communicator and handles the response
     * @pre gameName is not null
     * @pre there has been a successful login or register request before the call of this method
     * @post the logged in user has left the game or not based on whether or not the participant is a part of the gameName given
     * @post a message is return indicating success
     * @param gameName of type GameName, that contains the desired game
     * @return a string message detailing the result of the request
     */
    @Override
    public void leaveGame(GameName gameName, Callback c) {
        String url = "/leavegame";
        communicator.setGameName(gameName.getString());
        communicator.post(url, null,c);
    }

    /**
     * Sends a list of games request specific to the user to the communicator and handles the response
     * @pre there has been a successful login or register request before this call
     * @pre a valid username has been set for mUserName
     * @post ClientModelFacade's my games gets set or set to null if there are none
     * @post a message that shows either the success or failure of the call
     * @return a String message detailing the success or failure of the call
     */
    @Override
    public void getPlayerGames(Callback c) {
        String url = "/listofgames?username=" + communicator.getUsername();
        communicator.get(url, c);
    }

    /**
     * Sends a list of games request to the communicator and handles the response
     * @pre there has been a successful login or register request before this call
     * @post ClientModelFacade's available games gets set or set to null if there are none
     * @post a message that shows either the success or failure of the call
     * @return a String message detailing the success or failure of the call
     */
    @Override
    public void getAllGames(Callback c) {
        String url = "/listofgames";
        communicator.get(url, c);
    }
    /**
     * Sends a play game request to the communicator and handles the response
     * @pre there has been a successful login or register request before this call
     * @pre a valid username has been set for mUserName
     * @pre gameName is a game that the user has already joined and is a valid existing game name
     * @post ClientModelFacade's game Model gets initiatied
     * @post a message that shows either the success or failure of the call
     * @return a String message detailing the success or failure of the call
     */
    @Override
    public  void playGame(GameName gameName, Callback c){
        String url = "/playgame";
        communicator.setGameName(gameName.getString());
        communicator.get(url, c);
    }

    @Override
    public void doCommand(BaseCommand command, Callback c) {
        String url = "/commands";
        try {
            communicator.post(url, new JSONObject(Serializer.serialize(command)), c);
        }catch (JSONException e){

        }
    }

    @Override
    public void getCommands(int lastCommand, Callback c){
        String url = "/getcommands?lastCommand=" + lastCommand + "&gamename=" + communicator.getGameName();
        communicator.get(url, c);
    }

    public void getMessages(Callback c){
        String url = "/getmessages";
        communicator.get(url,c);
    }

    public void postMessage(String message, Callback c){
        String url = "/postmessage";
        JSONObject body = new JSONObject();
        try {
            body.put("message", message);
        }catch (JSONException e){

        }
        communicator.post(url,body, c);
    }

    /**
     * Starts the poller periodically running
     * @post the poller starts running
     */
    public void startGameListPolling(){
        if (this.gamePoller == null) {
            this.gamePoller = new GamePoller();
        } else {
            this.gamePoller.restart();
        }
    }

    /**
     * Stops the poller from periodically running
     * @post the poller stops running
     */
    public void stopGameListPolling(){
        gamePoller.stopPoller();
    }

    public void startCommandPolling(){
        //stopGameListPolling();
        if (this.commandPoller == null) {
            this.commandPoller = new CommandPoller();
        } else {
            commandPoller.restart();
        }
    }

    public void stopCommandPolling(){
        commandPoller.timer.cancel();
        commandPoller.timer.purge();
        commandPoller = null;
    }

    public void clearUserData(){
        communicator.setGameName(null);
        communicator.setAuthorizationToken(null);
        communicator.setGameName(null);
    }
}
