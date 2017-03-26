package goldenhammer.ticket_to_ride_client.model;

import java.util.ArrayList;
import java.util.List;


import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class CommandManager {

    private List<BaseCommand> commandList;

    public CommandManager(){
        commandList = new ArrayList<>();
    }

    public void addCommands(List<BaseCommand> newCommands) {
        for (BaseCommand command : newCommands) {
            if(commandList.size() == command.getCommandNumber()) {
                command.execute();
                commandList.add(command);
            }
        }
    }

    List<BaseCommand> getCommandList() {
        return commandList;
    }
}
