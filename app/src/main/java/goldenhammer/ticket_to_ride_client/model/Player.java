package goldenhammer.ticket_to_ride_client.model;

/**
 * Created by McKean on 2/3/2017.
 */

public class Player {
    private Username mUsername;
    private Password mPassword;

    public Player(Username username, Password password){
        mUsername = username;
        mPassword = password;
    }

    public Username getUsername(){
        return mUsername;
    }

    public Password getPassword(){
        return mPassword;
    }
}
