package goldenhammer.ticket_to_ride_client;

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

    public String getUsername(){
        return mUsername.getUsername();
    }

    public String getPassword(){
        return mPassword.getPassword();
    }
}
