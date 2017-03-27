package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Dialog;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
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
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;
import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;
import goldenhammer.ticket_to_ride_client.model.commands.DrawDestCardsCommand;
import goldenhammer.ticket_to_ride_client.model.commands.LayTrackCommand;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

import static java.lang.Character.toUpperCase;

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
                    model.addCommands(Serializer.deserializeCommands(res.getBody()));
                    //BaseCommand command = Serializer.deserializeCommand(res.getBody());
                    //List<BaseCommand> commands = new ArrayList<BaseCommand>();
                    //commands.add(command);
                   // model.addCommands(commands);
                } else {
                    showToast(Serializer.deserializeMessage(res.getBody()));
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
            if (selected.getOwner() != -1) {
                showToast("Track already claimed");
            } else {
                final Dialog dialog = new Dialog(owner);
                dialog.setContentView(R.layout.dialog_lay_track);
                final TextView text = (TextView) dialog.findViewById(R.id.lay_track_message);
                text.setText("Are you selecting the track between " + selected.getCity1().getName() + " and " + selected.getCity2().getName() + "?");
                Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                Button close = (Button) dialog.findViewById(R.id.retry_button);
                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!enoughCards(selected)) {
                            text.setText("Not enough Cards! Please select another track");
                            Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                            confirm.setVisibility(View.INVISIBLE);
                        }else {
                            state.layTrack(selected);
                            dialog.hide();
                        }
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
            }
        }
    }

    private boolean enoughCards(Track t){
        if(t.getColor() == null){
            return model.getHand().enoughTrainCards(t.getLength());
        }else{
            int cardCount = model.getHand().getWildTrainCards();
            if (t.getColor() == Color.RED) {
                cardCount += model.getHand().getRedTrainCards();
            } else if (t.getColor() == Color.ORANGE) {
                cardCount += model.getHand().getOrangeTrainCards();
            } else if (t.getColor() == Color.YELLOW) {
                cardCount += model.getHand().getYellowTrainCards();
            } else if (t.getColor() == Color.GREEN) {
                cardCount += model.getHand().getGreenTrainCards();
            } else if (t.getColor() == Color.BLUE) {
                cardCount += model.getHand().getBlueTrainCards();
            } else if (t.getColor() == Color.PURPLE) {
                cardCount += model.getHand().getPinkTrainCards();
            }else if (t.getColor() == Color.BLACK) {
                cardCount += model.getHand().getBlackTrainCards();
            } else if (t.getColor() == Color.WHITE) {
                cardCount += model.getHand().getWhiteTrainCards();
            }
            if(cardCount >= t.getLength()){
                return true;
            }
        }
        return false;
    }


    public void updateChat(){
        if (model.getMessages()!= null){// && !owner.getChat().equals(model.getMessages().getString())){
            owner.updateChat(ClientModelFacade.SINGLETON.getMessages().getString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(model.isEndGame()){
            owner.onEndGame();
        }else {
//        TODO: how should we handle selecting cards from the bank
            boolean isMyTurn = model.isMyTurn();
            if (!handInitialized && model.shouldInitializeHand()) {
                state = StateSelector.InitializeHand(this);
            } else if (isMyTurn) {
                handInitialized = true;
                BaseCommand previousCommand = model.getPreviousCommand();
                if (previousCommand instanceof DrawDestCardsCommand) {
                    state = StateSelector.MustReturnDestCard(this);
                } else {
                    state = StateSelector.MyTurn(this);
                }
            } else {
                handInitialized = true;
                state = StateSelector.NotMyTurn(this);
            }
            state.updateView();
        }
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

    public void sendLayTrackCommand(final Track track){
        final Dialog dialog = new Dialog(owner);
        dialog.setContentView(R.layout.dialog_card_select);
        dialog.setTitle(track.nametoString());
        TextView trackMessage = (TextView) dialog.findViewById(R.id.track_card_message);
        trackMessage.setText(track.infotoString());
        Button confirm = (Button) dialog.findViewById(R.id.confirm_cards_button);
        confirm.setEnabled(false);
        RecyclerView handlist = (RecyclerView) dialog.findViewById(R.id.cards);
        handlist.setLayoutManager(new LinearLayoutManager(owner));
        final HandAdapter handAdapter = new HandAdapter(model.getHand().getTrainCards(),track,confirm);
        handlist.setAdapter(handAdapter);
        dialog.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayTrackCommand command = new LayTrackCommand(model.getNextCommandNumber());
                command.setCards(handAdapter.getCards());
                command.setTrack(track);
                proxy.doCommand(command, myCommandCallback);
                dialog.dismiss();
            }
        });

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


   private class ViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnClickListener {
        TrainCard card;
        TextView cardText;
        CheckBox onOff;
        HandAdapter owner;

        public ViewHolder(View itemView, HandAdapter owner) {
            super(itemView);
            cardText = (TextView) itemView.findViewById(R.id.hand_card_item);
            onOff = (CheckBox) itemView.findViewById(R.id.use_card_item);
            itemView.setOnClickListener(this);
            onOff.setOnClickListener(this);
            this.owner = owner;
        }

        public void bindView(TrainCard card) {
            this.card = card;
            cardText.setText(card.getColor().toString());
        }

        @Override
        public void onClick(View v) {
            if(onOff.isChecked()){
                owner.addCard(card);
            }else{
                owner.removeCard(card);
            }
        }

    }


    private class HandAdapter extends RecyclerView.Adapter<ViewHolder> {
        ArrayList<TrainCard> cards;
        private List<TrainCard> hand;
        ViewHolder view;
        Track selected;
        Button confirm;

        public HandAdapter(List<TrainCard> hand, Track selected, Button confirm) {
            this.hand = hand;
            cards = new ArrayList<>();
            this.selected = selected;
            this.confirm = confirm;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.hand_list_item, parent, false);
            this.view = new ViewHolder(view, this);
            return this.view;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TrainCard card = hand.get(position);
            holder.bindView(card);
        }

        @Override
        public int getItemCount() {
            return hand.size();
        }

        public void addCard(TrainCard card){
            cards.add(card);
            buttonSet();
        }

        public void removeCard(TrainCard card){
            cards.remove(card);
            buttonSet();
        }

        private void buttonSet(){
            if(cards.size() == selected.getLength()){
                confirm.setEnabled(true);
            }else{
                confirm.setEnabled(false);
            }
        }
        public ArrayList<TrainCard> getCards(){
            return cards;
        }
    }


}
