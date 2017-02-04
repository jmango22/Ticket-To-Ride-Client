package goldenhammer.ticket_to_ride_client.model;

import android.icu.lang.UScript;

import java.io.IOException;

/**
 * Created by McKean on 2/3/2017.
 */

public class Username {
    private String username;

    public Username(String username) throws IOException {
        for (int i=0; i<username.length(); i++){
            if (!Character.isAlphabetic(username.charAt(i)) && !Character.isDigit(username.charAt(i))){
                throw new IOException("Invalid character");
            }
        }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
