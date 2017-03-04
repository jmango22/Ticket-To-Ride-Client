package goldenhammer.ticket_to_ride_client.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.commands.Command;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class CommandManager {

    private List<Command> commandList;

    public CommandManager(){
        commandList = new ArrayList<>();
    }

    public void addCommands(List<Command> newCommands) {
        for (Command command : newCommands) {
            if(commandList.size() == command.getCommandNumber() - 1) {
                command.execute();
                commandList.add(command);
            }
        }
    }

}
