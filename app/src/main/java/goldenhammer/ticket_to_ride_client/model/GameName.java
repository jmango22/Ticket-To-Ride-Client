package goldenhammer.ticket_to_ride_client.model;

import java.io.IOException;

/**
 * Created by McKean on 2/6/2017.
 */

public class GameName {
    private String name;

    public GameName(String name)throws IOException{
        if (name == null){
            throw new IOException("You must enter the name for your game!");
        }
        else if (name.length()<3){
            throw new IOException("Your name must have at least 3 characters!");
        }
        else if (name.length()>20){
            throw new IOException("Your game name must have fewer than 20 characters!");
        }
        for (int i=0; i<name.length(); i++){
            if (!Character.isAlphabetic(name.charAt(i)) && !Character.isDigit(name.charAt(i))){
                throw new IOException("Your game name may only have letters or numbers!");
            }
        }
        this.name = name;
    }

    public String getString(){
        return name;
    }

}
