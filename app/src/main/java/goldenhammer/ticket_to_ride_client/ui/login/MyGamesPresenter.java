package goldenhammer.ticket_to_ride_client.ui.login;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import goldenhammer.ticket_to_ride_client.communication.Callback;
import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.LocalProxy;
import goldenhammer.ticket_to_ride_client.communication.Results;
import goldenhammer.ticket_to_ride_client.communication.Serializer;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.GameName;

/**
 * Created by McKean on 2/6/2017.
 */

/**
 * MyGamesPresenter is responsible for conveying information to the MyGamesActivity, which shows a
 * list of the games that contain the current user as a player. This presenter is updated as the
 * model classes change. The presenter also calls methods in the serverProxy whenever the user
 * initiates and action.
 * @invariant The activity calling the presenter is the same as the variable owner.
 * @invariant This presenter is an observer of the ClientModelFacade
 * @invariant the proxy variable is some class that implements IProxy
 */
public class MyGamesPresenter implements Observer, IGameSelectorPresenter {
    private GameSelectorActivity owner;
    private IProxy proxy;

    /**
     * @pre The presenter is created by an instance of GameSelectorActivity, which is passed in
     * as a parameter (not null);
     * @post a new MyGamesPresenter is initialized and able to call appropriate methods.
     * @param activity The activity that is creating the presenter. This is used to call
     *                 methods in the activity after information is updated.
     */
    public MyGamesPresenter(GameSelectorActivity activity){
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        //proxy = LocalProxy.SINGLETON;
        ClientModelFacade.SINGLETON.addObserver(this);
    }

    /**
     * @pre the string name is not null
     * @post either calls the proxy's command to create a game or creates a warning toast.
     * @param name the desired name of the new game.
     */
    public void createGame(String name){
        try{
            GameName g = new GameName(name);
            proxy.createGame(g, new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(Serializer.deserializeMessage(res.getBody()));
                }
            });
//            String results = proxy.createGame(g);
//            owner.toastMessage(results);
        }catch(IOException e){
            owner.toastMessage(e.getMessage());
        }
    }

    /**
     * @pre the observable object o was changed and called the update method properly.
     * @param o Observable object that was changed.
     * @param arg argument of what was changed.
     */
    @Override
    public void update(Observable o, Object arg) {
        owner.setMyGameList(ClientModelFacade.SINGLETON.getMyGames().getAllGames());

        owner.runOnThisThread(new Runnable() {
            @Override
            public void run() {
                owner.update();
            }
        });

        if (ClientModelFacade.SINGLETON.getCurrentGame() != null){
            owner.onPlayGame();
        }
    }

    /**
     * @pre The string gameName is not null
     * @post either calls the serverProxy's function to join the designated game or creates a
     * toast if the string is not the proper format.
     * @param gameName the name of the game to be joined.
     */
    public void joinGame(String gameName){
        try {
            proxy.joinGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(Serializer.deserializeMessage(res.getBody()));
                }
            });
//            String results = proxy.joinGame(new GameName(gameName));
//            owner.toastMessage(results);
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());        }
    }

    /**
     *
     * @pre the gameName is not null.
     * @post calls the serverProxy's function to leave the designated game or creates a toast
     * if the gameName is not the proper format.
     * @param gameName the name of the game to leave.
     */
    public void leaveGame(String gameName){
        try {
            proxy.leaveGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    owner.toastMessage(Serializer.deserializeMessage(res.getBody()));
                }
            });
//            String results = proxy.leaveGame(new GameName(gameName));
//            owner.toastMessage(results);
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }

    /**
     * @pre the gameName is not null
     * @post either calls the serverProxy's playGame method to start the designated game
     * @param gameName the name of the game to be played.
     */
    public void playGame(String gameName){
        try {
            proxy.playGame(new GameName(gameName), new Callback() {
                @Override
                public void run(Results res) {
                    if(res.getResponseCode() == 200) {
                        ClientModelFacade.SINGLETON.setCurrentGame(Serializer.deserializeGameModel(res.getBody()));
                        ServerProxy.SINGLETON.stopGameListPolling();
                    } else {
                        owner.toastMessage("error: "+ res.getBody());
                    }
                }
            });
//            String results = proxy.playGame(new GameName(gameName));
//            owner.toastMessage(results);
        } catch (IOException e) {
            owner.toastMessage(e.getMessage());
        }
    }
}
