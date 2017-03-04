package goldenhammer.ticket_to_ride_client.model.commands;

/**
 * Created by McKean on 3/3/2017.
 */

public abstract class Command {
    String name;
    int commandNumber;
    public abstract void execute();
    public int getCommandNumber(){
        return commandNumber;
    }
}
