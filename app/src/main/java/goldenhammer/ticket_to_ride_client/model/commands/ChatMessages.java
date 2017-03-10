package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.ArrayList;

/**
 * Created by rache on 3/10/2017.
 */

public class ChatMessages {
    ArrayList<Message> messages;
    public ChatMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    public void setMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

}
