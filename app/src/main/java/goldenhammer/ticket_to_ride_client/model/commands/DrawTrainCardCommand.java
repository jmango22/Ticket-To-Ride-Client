package goldenhammer.ticket_to_ride_client.model.commands;

/**
 * Created by McKean on 3/3/2017.
 */

public class DrawTrainCardCommand extends Command {
    int slotNumber;

    public DrawTrainCardCommand(int commandNumber, int slotNumber){
        super(commandNumber);
        this.slotNumber = slotNumber;
    }

    @Override
    public void execute() {

    }
}
