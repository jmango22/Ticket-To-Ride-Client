package goldenhammer.ticket_to_ride_client;

import java.io.IOException;

/**
 * Created by McKean on 2/3/2017.
 */

public class Password{
    private String password;

    public Password(String password) throws IOException {
        for (int i=0; i<password.length(); i++){
            if (!Character.isAlphabetic(password.charAt(i)) && !Character.isDigit(password.charAt(i))){
                throw new IOException("Invalid character");
            }
        }
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
