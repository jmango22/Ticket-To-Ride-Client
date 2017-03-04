package goldenhammer.ticket_to_ride_client.model.commands;

/**
 * Created by McKean on 3/3/2017.
 */

public abstract class Command {
    String name;
    int playerNumber;
    int commandNumber;

    public Command(int commandNumber, int playerNumber){
        this.commandNumber = commandNumber;
        this.name = this.getClass().toString();
        this.playerNumber = playerNumber;
    }

    //TODO: need to remove this constructor, it should always have the playerNumber
    public Command(int commandNumber){
        this.commandNumber = commandNumber;
        this.name = this.getClass().toString();
    }

    public int getPlayerNumber(){
        return playerNumber;
    }
    public abstract void execute();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommandNumber() {
        return commandNumber;
    }

    public void setCommandNumber(int commandNumber) {
        this.commandNumber = commandNumber;
    }
}
