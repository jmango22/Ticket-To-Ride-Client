package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.communication.Callback;
import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.LocalProxy;
import goldenhammer.ticket_to_ride_client.communication.Results;
import goldenhammer.ticket_to_ride_client.communication.Serializer;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;
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
    private State state;
    private boolean handInitialized;

    public GamePlayPresenter(GamePlayActivity activity) {
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        //proxy = LocalProxy.SINGLETON;
        model = ClientModelFacade.SINGLETON;
        model.addObserver(this);
        name = model.getCurrentGame().getGameName();
        myCommandCallback = new Callback() {
            @Override
            public void run(Results res) {
                if(res.getResponseCode() < 400) {
                    List<Command> commands = Serializer.deserializeCommands(res.getBody());
                    model.addCommands(commands);
                } else {
                    showToast(Serializer.deserializeMessage(res.getBody()));
                }
            }
        };
        state = StateSelector.MyTurn(this);
        handInitialized = false;

    }


    @Override
    public void update(Observable o, Object arg) {

//        TODO: how should we handle selecting cards from the bank
        boolean isMyTurn = model.isMyTurn();
        if (!handInitialized && model.shouldInitializeHand()) {
            state = StateSelector.InitializeHand(this);
        }
        else if (isMyTurn) {
            handInitialized = true;
            Command previousCommand = model.getPreviousCommand();
            if (previousCommand instanceof DrawDestCardsCommand){
                state = StateSelector.MustReturnDestCard(this);
            } else {
                state = StateSelector.MyTurn(this);
            }
        }
        else {
            handInitialized = true;
            state = StateSelector.NotMyTurn(this);
        }
        state.updateView();
    }

    @Override
    public void takeTrainCards() {
        state.takeDestCards();
    }

    @Override
    public void takeDestCards() {
        state.takeDestCards();
    }

    void sendTakeDestCardsCommand() {
        DrawDestCardsCommand command = new DrawDestCardsCommand(1);
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void returnDestCards(List<DestCard> toReturn) {
        state.returnDestCards(toReturn);
    }

    void sendReturnDestCardsCommand(List<DestCard> toReturn) {
        ReturnDestCardsCommand command = new ReturnDestCardsCommand(1, toReturn);
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void layTrack(Track track) {
        state.layTrack(track);
    }

    @Override
    public void loadGame() {

    }


    boolean mustInitialize = true;
    protected void initializeHand() {
        if(mustInitialize) {
            owner.initializeHand(model.getHand());
            mustInitialize = false;
        }
    }

    protected void updateBank(){
        if (model.getAllBankTrainCardsArray()!= null) {
            owner.updateBank(model.getAllBankTrainCardsArray());
        }

    }

    protected void updateMap() {
        owner.drawMap(model.getCurrentGame().getMap());
    }

    protected void updatePlayers(){
        owner.updatePlayers(model.getLeaderboard());
    }

    protected void updateCurrentTurn(){
        owner.updateTurn(model.getCurrentTurnPlayer());
    }

    protected void updateHand(){
        owner.updateHand(model.getHand());
    }

    protected void showToast(String message) {
        owner.toastMessage(message);
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
