package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.communication.Callback;
import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.Results;
import goldenhammer.ticket_to_ride_client.communication.Serializer;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.commands.Command;
import goldenhammer.ticket_to_ride_client.model.commands.DrawDestCardsCommand;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;

/**
 * Created by devonkinghorn on 2/22/17.
 */

public class GamePlayPresenter implements Observer, IGamePlayPresenter {
    private GamePlayActivity owner;
    private IProxy proxy;
    private ClientModelFacade model;
    private Callback myCommandCallback;
    private GameName name;
    private DoAction actionState;
    private DoUpdate updateState;

    public GamePlayPresenter(GamePlayActivity activity) {
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        model = ClientModelFacade.SINGLETON;
        model.addObserver(this);
        name = model.getCurrentGame().getGameName();
        myCommandCallback = new Callback() {
            @Override
            public void run(Results res) {
                List<Command> commands = Serializer.deserializeCommands(res.getBody());
                //TODO: tell the facade to take the new commands.
            }
        };
        actionState = ActionSelector.MyTurn(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        boolean isMyTurn = model.isMyTurn();
        if (isMyTurn) {
            Command previousCommand = model.getPreviousCommand();
            if (previousCommand.getClass() == null ){

            }
        } else {
            actionState = ActionSelector.NotMyTurn(this);
            updateState = UpdateSelector.NotMyTurn(this);
        }

    }

    @Override
    public void takeTrainCards() {

    }

    @Override
    public void takeDestCards() {
        actionState.takeDestCards();
    }

    void sendTakeDestCardsCommand() {
        DrawDestCardsCommand command = new DrawDestCardsCommand(1);
        proxy.doCommand(this.name, command, myCommandCallback);
    }

    @Override
    public void returnDestCards(List<DestCard> toReturn) {
        actionState.returnDestCards(toReturn);
    }

    void sendReturnDestCardsCommand(List<DestCard> toReturn) {
        ReturnDestCardsCommand command = new ReturnDestCardsCommand(1, toReturn);
        proxy.doCommand(this.name, command, myCommandCallback);
    }

    @Override
    public void layTrack(Track track) {

    }

    @Override
    public void loadGame() {

    }

    @Override
    public void update() {

    }

    public void demo() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hey");

//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println("jude");
//                    }
//                },1000);
            }
        },1000);
    }
}
