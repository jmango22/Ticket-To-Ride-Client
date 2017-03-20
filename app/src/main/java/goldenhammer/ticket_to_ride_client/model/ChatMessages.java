package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rache on 3/10/2017.
 */

public class ChatMessages {
    List<Message> messages;
    public ChatMessages(List<Message> messages){
        this.messages = messages;
    }

    public void setMessages(List<Message> messages){
        this.messages = messages;
    }

    public List<Message> getMessages(){
        return messages;
    }

    public String getString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < messages.size(); i++){
            Message m = messages.get(i);
            sb.append(m.getUsername());
            sb.append(": ");
            sb.append(m.getMessage());
            sb.append('\n');
            //sb.append(System.getProperty("line.seperator"));
        }
        return sb.toString();
    }

}
