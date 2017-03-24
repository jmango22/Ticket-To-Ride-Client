package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Dialog;
import android.graphics.PointF;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.communication.Callback;
import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.MessagePoller;
import goldenhammer.ticket_to_ride_client.communication.Results;
import goldenhammer.ticket_to_ride_client.communication.Serializer;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.commands.Command;
import goldenhammer.ticket_to_ride_client.model.commands.DrawDestCardsCommand;
import goldenhammer.ticket_to_ride_client.model.commands.LayTrackCommand;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

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
    private MessagePoller poller;

    public GamePlayPresenter(GamePlayActivity activity) {
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        //proxy = LocalProxy.SINGLETON;
        model = ClientModelFacade.SINGLETON;

        name = model.getCurrentGame().getGameName();
        myCommandCallback = new Callback() {
            @Override
            public void run(Results res) {
                if(res.getResponseCode() < 400) {
                    Command command = Serializer.deserializeCommand(res.getBody());
                    List<Command> commands = new ArrayList<Command>();
                    commands.add(command);
                    model.addCommands(commands);
                } else {
                    showToast(Serializer.deserializeMessage(res.getBody()));
                    model.changed();
                }
            }
        };
        state = StateSelector.MyTurn(this);
        handInitialized = false;
    }

    public State getState(){
        return state;
    }

    @Override
    public void onPause() {
        model.deleteObserver(this);
    }

    @Override
    public void onResume() {
        model.addObserver(this);
    }

    public void clickTrack(PointF pt){
        //CURRENTLY THE POINT IS IN COORDINATES ASSOCIATED WITH THE SCREEN AND NOT WORLD COORDINATES
        final Track selected = state.onTouchEvent(pt, model.getAllTracks());
        if(selected != null) {
            if (selected.getOwner() == -1) {
                showToast("Track already claimed");
            } else {
                final Dialog dialog = new Dialog(owner);
                dialog.setContentView(R.layout.dialog_lay_track);
                TextView text = (TextView) dialog.findViewById(R.id.lay_track_message);
                text.setText("Are you selecting the track between " + selected.getCity1().getName() + " and " + selected.getCity2().getName() + "?");
                Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                Button close = (Button) dialog.findViewById(R.id.retry_button);
                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        state.layTrack(selected);
                        dialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }


    public void updateChat(){
        if (model.getMessages()!= null){// && !owner.getChat().equals(model.getMessages().getString())){
            owner.updateChat(ClientModelFacade.SINGLETON.getMessages().getString());
        }
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
    public void takeTrainCards(int index) {
        state.takeDestCards();
    }

    public void sendTakeTrainCardsCommand(int index){
       // DrawTrainCardCommand command = new DrawTrainCardCommand(index);
        //proxy.doCommand(command,myCommandCallback);
    }

    @Override
    public void takeDestCards() {
        state.takeDestCards();
    }

    public void sendTakeDestCardsCommand() {
        DrawDestCardsCommand command = new DrawDestCardsCommand(1);
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void returnDestCards(List<DestCard> toReturn) {
        state.returnDestCards(toReturn);
    }

    public void sendReturnDestCardsCommand(List<DestCard> toReturn) {
        ReturnDestCardsCommand command = new ReturnDestCardsCommand(model.getNextCommandNumber(), toReturn);
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void layTrack(Track track) {
        state.layTrack(track);
    }

    public void sendLayTrackCommand(Track track){
        //LayTrackCommand command = new LayTrackCommand(model.getNextCommandNumber(), track);
        //proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void loadGame() {

    }

    public void endGame(){
        owner.onEndGame();
    }



    public void initializeHand() {
        owner.initializeHand(model.getHand());
    }

    public void updateBank(){
        if (model.getAllBankTrainCardsArray()!= null) {
            owner.updateBank(model.getAllBankTrainCardsArray());
        }

    }
    public void startDestCardsDialog(){
        owner.destCardsDialog();
    }


    public void startTracksDialog(){
        owner.tracksDialog();
    }

    public void startTrainCardsDialog(){
        owner.trainCardsDialog();
    }

    public void clickTrainCards(){
        state.clickTrainCards();
    }

    public void clickDestCards(){
        state.clickDestCards();
    }

    public void clickTracks(){
        state.clickTracks();
    }

    public void updateMap() {
        owner.drawMap(model.getCurrentGame().getMap());
    }

    public void updatePlayers(){
        owner.updatePlayers(model.getLeaderboard());
    }

    public void updateCurrentTurn(){
        owner.updateTurn(model.getCurrentTurnPlayer());
    }

    public void updateHand(){
        owner.updateHand(model.getHand());
    }

    public void onChatOpen(){
        if(poller == null) {
            poller = new MessagePoller();
        }else{
            poller.restart();

        }
    }

    public void onChatClose(){
        poller.stopPoller();
    }

    public void postMessage(String message){
        proxy.postMessage(message, new Callback() {
            @Override
            public void run(Results res) {
                showToast(Serializer.deserializeMessage(res.getBody()));
            }
        });
    }

    public void showToast(String message) {
        owner.toastMessage(message);
    }

    public void demo() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hey");

            }
        },1000);
    }
}
