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
    private Poller poller;
    private ServerProxy(){}
    private String mUsername;
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
     * returns the saved username and handles the response
     * @post The saved username will be returned, or null if there is no username saved
     * @return the string representing the saved username. Null may be returned if there is no Username
     */
    public String getUsername(){
        return mUsername;
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
    public String joinGame(GameName gameName) {
        String url = "/joingame";
        return communicator.post(url, null, gameName.getString());
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
    public String leaveGame(GameName gameName) {
        String url = "/leavegame";
        return communicator.post(url, null, gameName.getString());
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
    public String getPlayerGames() {
        String url = "/listofgames?username=" + mUsername;
        String returnMessage = communicator.get(url, null);
        if(returnMessage.equals("Success!")){
            ClientModelFacade.SINGLETON.setMyGames(deserializeGames());
        }
        return returnMessage;
    }

    /**
     * Sends a list of games request to the communicator and handles the response
     * @pre there has been a successful login or register request before this call
     * @post ClientModelFacade's available games gets set or set to null if there are none
     * @post a message that shows either the success or failure of the call
     * @return a String message detailing the success or failure of the call
     */
    @Override
    public String getAllGames() {
        String url = "/listofgames";
        String returnMessage = communicator.get(url, null);
        if(returnMessage.equals("Success!")){
            ClientModelFacade.SINGLETON.setAvailableGames(deserializeGames());
        }
        return returnMessage;
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

    /**
     * Takes the results from the communicator and converts the string to a class of type GameList
     * @pre communicator's value results were set in a call and that value was a JSON Object of type GameList
     * @return a GameList of the communicator's results
     */
    private GameList deserializeGames(){
        Gson gson = new Gson();
        return gson.fromJson(communicator.getResults(),GameList.class);
    }

    /**
     * Takes the results from the communicator and converts the string to a class of type GameModel
     * @pre communicator's value results were set in a call and that value was a JSON Object of type GameModel
     * @return a GameModel of the communicator's results
     */
    private GameModel deserializeGameModel(){
        Gson gson = new Gson();
        return gson.fromJson(communicator.getResults(), GameModel.class);
    }
    /**
     * Starts the poller periodically running
     * @post the poller starts running
     */
    public void startGameListPolling(){
            poller = new Poller();
    }

    /**
     * Stops the poller from periodically running
     * @post the poller stops running
     */
    public void stopGameListPolling(){
        poller = null;
    }
}
