package goldenhammer.ticket_to_ride_client.model.commands;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by McKean on 3/3/2017.
 */

public abstract class Command {
    String name;
    int playerNumber;
    int commandNumber;
    String playerName;

    public Command(int commandNumber, int playerNumber){
        this.commandNumber = commandNumber;
        this.name = this.getClass().toString();
        this.playerNumber = playerNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Command(int commandNumber){
        this.commandNumber = commandNumber;
        this.name = this.getClass().toString();
        this.playerNumber = ClientModelFacade.SINGLETON.getMyPlayerNumber();
    }
    public Command(){
        this.commandNumber = ClientModelFacade.SINGLETON.getNextCommandNumber();
        this.name = this.getClass().toString();
        this.playerNumber = ClientModelFacade.SINGLETON.getMyPlayerNumber();
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

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
