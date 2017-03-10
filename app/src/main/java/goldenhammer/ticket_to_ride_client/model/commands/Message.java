package goldenhammer.ticket_to_ride_client.model.commands;

/**
 * Created by rachel on 3/10/2017.
 */

public class Message {
    private String playerID;
    private String message;
    private String messageID;
    private String gameID;

    public Message(String messageID, String gameID, String playerID, String message) {
        this.messageID = messageID;
        this.gameID = gameID;
        this.playerID = playerID;
        this.message = message;
    }

    public Message(String username, String message){
        this.playerID = username;
        this.message = message;
    }

    public Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public String getUsername(){
        return playerID;
    }
}
