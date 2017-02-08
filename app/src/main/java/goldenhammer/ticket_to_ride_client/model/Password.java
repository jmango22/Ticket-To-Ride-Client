package goldenhammer.ticket_to_ride_client.model;

import java.io.IOException;

/**
 * Created by McKean on 2/3/2017.
 */

public class Password{
    private String password;

    public Password(String password) throws IOException {
        if (password == null){
            throw new IOException("You must enter a password!");
        }
        else if (password.length()<3){
            throw new IOException("Your password must be at least 3 characters!");
        }
        else if (password.length()>20){
            throw new IOException("Your password must be fewer than 20 characters!");
        }
        for (int i=0; i<password.length(); i++){
            if (!Character.isAlphabetic(password.charAt(i)) && !Character.isDigit(password.charAt(i))){
                throw new IOException("Invalid character");
            }
        }
        this.password = password;
    }

    public String getString() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
