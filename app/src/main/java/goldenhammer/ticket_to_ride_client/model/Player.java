package goldenhammer.ticket_to_ride_client.model;

import java.util.List;

/**
 * Created by McKean on 2/3/2017.
 */

public class Player {
    private Username mUsername;

    public Player(Username username, Password password){
        mUsername = username;
    }

    public Username getUsername(){
        return mUsername;
    }



}
