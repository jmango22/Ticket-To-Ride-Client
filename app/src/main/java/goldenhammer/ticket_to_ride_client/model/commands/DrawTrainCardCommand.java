package goldenhammer.ticket_to_ride_client.model.commands;

/**
 * Created by McKean on 3/3/2017.
 */

public abstract class DrawTrainCardCommand extends Command {
    int slotNumber;

    public DrawTrainCardCommand(int commandNumber){
        super(commandNumber);
    }

    @Override
    public void execute() {

    }
}
