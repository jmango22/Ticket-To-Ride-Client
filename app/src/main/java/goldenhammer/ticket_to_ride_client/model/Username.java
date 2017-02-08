package goldenhammer.ticket_to_ride_client.model;

import android.icu.lang.UScript;

import java.io.IOException;

/**
 * Created by McKean on 2/3/2017.
 */

public class Username {
    private String username;

    public Username(String username) throws IOException {
        if (username == null){
            throw new IOException("You must enter a username!");
        }
        else if (username.length()<3){
            throw new IOException("Your username must have at least 3 characters!");
        }
        else if (username.length()>20){
            throw new IOException("Your username must have fewer than 20 characters!");
        }
        for (int i=0; i<username.length(); i++){
            if (!Character.isAlphabetic(username.charAt(i)) && !Character.isDigit(username.charAt(i))){
                throw new IOException("Your username may only contain letters and numbers");
            }
        }
        this.username = username;
    }

    public String getString() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
