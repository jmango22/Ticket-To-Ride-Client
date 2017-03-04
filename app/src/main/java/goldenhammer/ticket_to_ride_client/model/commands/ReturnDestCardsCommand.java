package goldenhammer.ticket_to_ride_client.model.commands;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;

/**
 * Created by McKean on 3/3/2017.
 */

public class ReturnDestCardsCommand extends Command {
    List<DestCard> toReturn;
    public ReturnDestCardsCommand(List<DestCard> toReturn) {
        this.toReturn = toReturn;
    }
    @Override
    public void execute() {

    }
}
