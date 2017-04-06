package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Dialog;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

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
import goldenhammer.ticket_to_ride_client.model.commands.DrawDestCardsCommand;
import goldenhammer.ticket_to_ride_client.model.commands.DrawTrainCardCommand;
import goldenhammer.ticket_to_ride_client.model.commands.LayTrackCommand;
import goldenhammer.ticket_to_ride_client.model.commands.ReturnDestCardsCommand;
import goldenhammer.ticket_to_ride_client.ui.play.states.EndGameState;
import goldenhammer.ticket_to_ride_client.ui.play.states.State;
import goldenhammer.ticket_to_ride_client.ui.play.states.StateSelector;

import static java.lang.Character.toString;
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
    private boolean handInitialized;
    private MessagePoller poller;

    public GamePlayPresenter(GamePlayActivity activity) {
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        ServerProxy.SINGLETON.startCommandPolling();
        //proxy = LocalProxy.SINGLETON;
        model = ClientModelFacade.SINGLETON;

        name = model.getCurrentGame().getGameName();
        myCommandCallback = new Callback() {
            @Override
            public void run(Results res) {
                if(res.getResponseCode() < 400) {
                    model.addCommands(Serializer.deserializeCommands(res.getBody()));
                } else {
                    showToast(Serializer.deserializeMessage(res.getBody()));
                }
            }
        };
        StateSelector.setPresenter(this);
        model.setState(new State(this));
        handInitialized = false;
    }

    public void closeTrainCardsDialog(){
        owner.closeTrainCardsDialog();
    }

    public void closeInitHandDialog(){
        owner.closeInitHandDialog();
    }

    public void closeReturnDestCardsDialog(){
        owner.closeReturnDestCardsDialog();
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
        ArrayList<Track> selectedTracks = testingBounds(model.getState().onTouchEvent(pt, model.getAllTracks()));
        final Track selected = selectedTracks.get(0);
        final Track secondT = selectedTracks.get(1);
        if(selected!= null){
                final Dialog dialog = new Dialog(owner);
                dialog.setContentView(R.layout.dialog_lay_track);
                final TextView title = (TextView) dialog.findViewById(R.id.titleText);
                title.setText("Are you selecting the track between " + selected.getCity1().getName() + " and " + selected.getCity2().getName() + "?");
                final RadioGroup button = (RadioGroup) dialog.findViewById(R.id.radiogroup);
                if(secondT != null)
                {
                    TextView text = (TextView) dialog.findViewById(R.id.lay_track_message);
                    text.setText(selected.infotoString());
                    button.setVisibility(View.VISIBLE);
                    TextView second = (TextView) dialog.findViewById(R.id.second_track_message);
                    second.setText(secondT.infotoString());
                }
                Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                Button close = (Button) dialog.findViewById(R.id.retry_button);
                dialog.show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Track selectedTrack = selected;
                        if(secondT != null) {
                            if (button.getCheckedRadioButtonId() == R.id.firstTrackCheck) {
                                selectedTrack = selected;
                            } else {
                                selectedTrack = secondT;
                            }
                        }
                        ClientModelFacade model = ClientModelFacade.SINGLETON;
                        if(selectedTrack.getLength() > model.getLeaderboard().get(model.getMyPlayerNumber()).getNumPieces()) {
                            title.setText("Not enough Trains! Please select another track");
                            Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                            confirm.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.INVISIBLE);
                            TextView text = (TextView) dialog.findViewById(R.id.lay_track_message);
                            text.setText("");
                            text = (TextView) dialog.findViewById(R.id.second_track_message);
                            text.setText("");
                        }else if(!enoughCards(selectedTrack)) {
                            title.setText("Not enough Cards! Please select another track");
                            Button confirm = (Button) dialog.findViewById(R.id.lay_track_button);
                            confirm.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.INVISIBLE);
                            TextView text = (TextView) dialog.findViewById(R.id.lay_track_message);
                            text.setText("");
                            text = (TextView) dialog.findViewById(R.id.second_track_message);
                            text.setText("");
                        }else {
                           model.getState().layTrack(selectedTrack);
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

    private ArrayList<Track> testingBounds(Track t){
        ArrayList<Track> oneAndTwo = new ArrayList<>();
        oneAndTwo.add(null);
        oneAndTwo.add(null);
        if(t != null){
            Track second = secondTrack(t);
            if(second != null){
                if((t.getOwner() == -1)&&(second.getOwner() == -1)){
                    oneAndTwo.set(0,t);
                    oneAndTwo.set(1,second);
                }else if(model.getLeaderboard().size() >= 4){
                    if ((t.getOwner() == -1) && (second.getOwner() != model.getMyPlayerNumber())) {
                        oneAndTwo.set(0, t);
                    } else if ((second.getOwner() == -1) && (t.getOwner() != model.getMyPlayerNumber())) {
                        oneAndTwo.set(0, second);
                    } else {
                        showToast("Track both claimed or you have claimed one side!");
                    }
                }else {
                    showToast("One side claimed and not enough players to claim the other side!");
                }
            }else{
                if(t.getOwner() == -1){
                    oneAndTwo.set(0,t);
                }else{
                    showToast("Track already claimed!");
                }
            }
        }
        return oneAndTwo;
    }

    private Track secondTrack(Track track) {
        for (Track list : model.getAllTracks()) {
            if (list.getCity1().getName().equals(track.getCity1().getName()) && list.getCity2().getName().equals(track.getCity2().getName())) {
                if(list.getSecondTrack() || track.getSecondTrack()) {
                    return list;
                }
            }
        }
        return null;
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
        if(model.getState() instanceof EndGameState){
            owner.onEndGame();
        }else {
            model.getState().updateView();
        }
    }

    public void updateTitle(String title){
        if (model.getLastRound()){
            owner.updateTitle("Last Round: " + title);
        }
        else {
            owner.updateTitle(title);
        }
    }

    @Override

    public boolean takeTrainCards(int index) {
        return model.getState().takeTrainCards(index);
    }

    public void sendTakeTrainCardsCommand(int index){

       DrawTrainCardCommand command = new DrawTrainCardCommand(model.getNextCommandNumber(), index);
        command.setAsMyCommand();
        if(index >=0 && index <=4)
            command.setCard(model.getAllBankTrainCards().get(index));
        proxy.doCommand(command,myCommandCallback);

    }

    @Override
    public void takeDestCards() {
        model.getState().takeDestCards();
    }

    public void sendTakeDestCardsCommand() {
        DrawDestCardsCommand command = new DrawDestCardsCommand(model.getNextCommandNumber());
        command.setAsMyCommand();
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void returnDestCards(List<DestCard> toReturn) {
        model.getState().returnDestCards(toReturn);
    }

    public void sendReturnDestCardsCommand(List<DestCard> toReturn) {
        ReturnDestCardsCommand command = new ReturnDestCardsCommand(model.getNextCommandNumber(), toReturn);
        command.setAsMyCommand();
        proxy.doCommand(command, myCommandCallback);
    }

    @Override
    public void layTrack(Track track) {
        model.getState().layTrack(track);
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
                    command.setAsMyCommand();
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
        proxy.doCommand(new DrawDestCardsCommand(model.getNextCommandNumber()),myCommandCallback);
        owner.returnDestCardsDialog(model.getHand().getDrawnDestCards());
    }

    public void startTracksDialog(){
        owner.tracksDialog();
    }

    public void startTrainCardsDialog(){
        owner.trainCardsDialog();
    }

    public void clickTrainCards(){
        model.getState().clickTrainCards();
    }

    public void clickDestCards(){
        model.getState().clickDestCards();
    }
    public void takeDestCardsDialog(){
        owner.returnDestCardsDialog(model.getHand().getDrawnDestCards());
    }

    public void clickTracks(){
        model.getState().clickTracks();
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
       int position;

        public ViewHolder(View itemView, HandAdapter owner) {
            super(itemView);
            cardText = (TextView) itemView.findViewById(R.id.hand_card_item);
            onOff = (CheckBox) itemView.findViewById(R.id.use_card_item);
            itemView.setOnClickListener(this);
            onOff.setOnClickListener(this);
            this.owner = owner;
        }

        public void bindView(TrainCard card, int checked, int position) {
            this.card = card;
            this.position = position;
            if(checked ==  0){
                onOff.setChecked(false);
            }else{
                onOff.setChecked(true);
            }
            cardText.setText(card.getColor().toString());
        }

        @Override
        public void onClick(View v) {
            if(onOff.isChecked()){
                owner.addCard(card, position);
            }else{
                owner.removeCard(card, position);
            }
        }

    }


    private class HandAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<TrainCard> cards;
        private List<TrainCard> hand;
        private ArrayList<Integer> checked = new ArrayList<>();
        ViewHolder view;
        Track selected;
        Button confirm;

        public HandAdapter(List<TrainCard> hand, Track selected, Button confirm) {
            this.hand = hand;
            Collections.sort(this.hand);
            for(int i = 0 ; i < hand.size();i++){
                checked.add(0);
            }
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
            holder.bindView(card, checked.get(position), position);
        }

        @Override
        public int getItemCount() {
            return hand.size();
        }

        public void addCard(TrainCard card, int position){
            cards.add(card);
            checked.set(position, 1);
            buttonSet();
        }

        public void removeCard(TrainCard card,int position){
            cards.remove(card);
            checked.set(position, 0);
            buttonSet();
        }


        private void buttonSet(){
            if ((cards.size() == selected.getLength()) && (checkCards(cards,selected))) {
                confirm.setEnabled(true);
            } else {
                confirm.setEnabled(false);
            }
        }

        private boolean checkCards(ArrayList<TrainCard> cards, Track selected){
            Color color = selected.getColor();
            if (color == null) {
                if(cards.get(0).getColor() != Color.WILD){
                    color = cards.get(0).getColor();
                }else{
                    if(cards.size() >1){
                        color = cards.get(1).getColor();
                    }
                }
            }
            for(TrainCard c: cards){
                if((c.getColor() != color) &&(c.getColor() != Color.WILD)){
                    return false;
                }
            }
            return true;
        }

        public ArrayList<TrainCard> getCards(){
            return cards;
        }
    }


}
