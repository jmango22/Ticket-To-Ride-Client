package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Dialog;
import android.content.res.Resources;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.communication.LocalProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.DrawnDestCards;
import goldenhammer.ticket_to_ride_client.model.GameModel;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.model.Map;
import goldenhammer.ticket_to_ride_client.model.PlayerOverview;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;

//TODO Dialog for selecting cards
//TODO Dialog for initializing Hand
//TODO Functions to draw the map, tracks, etc.
//TODO Buttons for each action
//TODO Demo Button
public class GamePlayActivity extends AppCompatActivity {
    private String[] mNavDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<PlayerOverview> players;
    private Hand hand;
    private TextView selectedView;
    private int selectedIndex;

    private TextView selectedTrainCard;
    private int selectedTrainIndex;

    private boolean[] returningDestCards = new boolean[3];

    private GamePlayPresenter presenter;
    private DrawnDestCards drawnDestCards;
    private int screenHeight;
    private int screenWidth;
    private ImageView mapView;
    private int mapX = 1707;
    private int mapY = 1223;
    private float mapScaleX;
    private float mapScaleY;
    private int mapWindowHeight= 500;//488;
    private int mapWindowWidth = 774;

    private  Dialog chats;
    private String chatString;
    private TextView chatText;
    private String chatToSend;
    private boolean initialized;

    private Dialog dTrainCards;
    private TextView tSlot0;
    private TextView tSlot1;
    private TextView tSlot2;
    private TextView tSlot3;
    private TextView tSlot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play2);
        presenter = new GamePlayPresenter(this);
//        ServerProxy.SINGLETON.stopGameListPolling();
        chats = new Dialog(GamePlayActivity.this);
        chatString = "";
        chatText = (TextView) chats.findViewById(R.id.chat_text);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        mapView = (ImageView) findViewById(R.id.map_image);
        mapView.getHeight();
        //mapView.setImageResource(R.drawable.map);
        Button destButton = (Button) findViewById(R.id.dest_button);
        Button leaderboardButton = (Button) findViewById(R.id.leaderboard_button);
        Button demoButton = (Button) findViewById(R.id.demo_button);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         screenHeight = displayMetrics.heightPixels;
         screenWidth = displayMetrics.widthPixels;
        mapScaleX = (float)(mapWindowWidth)/(float)mapX;
        mapScaleY = (float)(mapWindowHeight)/(float)mapY;

        initialized = false;
        dTrainCards = new Dialog(GamePlayActivity.this);
        dTrainCards.setContentView(R.layout.dialog_train_cards);

        tSlot0 = (TextView) dTrainCards.findViewById(R.id.t_card_slot_0);
        tSlot1 = (TextView) dTrainCards.findViewById(R.id.t_card_slot_1);
        tSlot2 = (TextView) dTrainCards.findViewById(R.id.t_card_slot_2);
        tSlot3 = (TextView) dTrainCards.findViewById(R.id.t_card_slot_3);
        tSlot4 = (TextView) dTrainCards.findViewById(R.id.t_card_slot_4);

        destButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destCardsDialog();
            }
        });

        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLeaderBoard();
            }
        });

        demoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.demo();
                placeHolders();
            }
        });
        presenter.updateHand();
        presenter.updateMap();
        presenter.updatePlayers();
        presenter.updateBank();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
        ServerProxy.SINGLETON.stopCommandPolling();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
        ServerProxy.SINGLETON.startCommandPolling();
    }

    public void placeHolders(){
       // ServerProxy.SINGLETON.stopCommandPolling();
        GameModel m = ClientModelFacade.SINGLETON.getCurrentGame();
        LocalProxy.SINGLETON.playGame(null,null);
        int handSize = ClientModelFacade.SINGLETON.getUserDestCards().size();
        presenter.updateBank();
        //GameModel n = ClientModelFacade.SINGLETON.getCurrentGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game_play,menu);
        return true;
    }

    public List<DestCard> initializeHand(Hand hand){
        updateHand(hand);
        this.drawnDestCards = hand.getDrawnDestCards();
        if (!initialized) {
            initialized = true;
            initHandDialog(drawnDestCards);
        }
        return null;
    }
    public void setSelected(TextView view, int index){
        if (this.selectedView != null){
            this.selectedView.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.card_black));
        }
        this.selectedView = view;
        this.selectedIndex = index;
        view.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.cyan));
    }

    public void setSelectedDestCard(TextView view, int index){
        if (returningDestCards[index]){
            view.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.card_black));
            returningDestCards[index] = false;
        }
        else{
            view.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.cyan));
            returningDestCards[index] = true;
        }
    }

    public void returnInitDestCards(){
        List<DestCard> toReturn = new ArrayList<>();
        if (selectedIndex != -1) {
            toReturn.add(drawnDestCards.getRemainingDestCards().get(selectedIndex));
        }
         presenter.returnDestCards(toReturn);
    }

    public void returnDestCards(){
        List<DestCard> toReturn = new ArrayList<>();
        for (int i=0; i<3; i++){
            if (returningDestCards[i]){
                toReturn.add(drawnDestCards.getRemainingDestCards().get(i));
            }
        }
        presenter.returnDestCards(toReturn);
    }

    public void updateChat(String chatString){
        if (!this.chatString.equals(chatString)) {
            this.chatString = chatString;
            if (chatText != null) {
                chatText.setText(this.chatString);
            }
        }
    }

    public String getChat(){
        return chatString;
    }

    public void chatDialog(){
        chats.setTitle("Chats");
        chats.setContentView(R.layout.dialog_chat);

        presenter.onChatOpen();

        if (chatText == null){
            chatText = (TextView) chats.findViewById(R.id.chat_text);
            chatText.setText(chatString);
        }
        final EditText chatEditText = (EditText) chats.findViewById(R.id.chat_edit_text);
        Button sendButton = (Button) chats.findViewById(R.id.send_chat_button);
        Button closeButton = (Button) chats.findViewById(R.id.chat_close_button);
        //chatText.setText(chatString);

        chatEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                chatToSend = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.postMessage(chatToSend);
                chatEditText.setText("");
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chats.hide();
                presenter.onChatClose();
            }
        });
        chats.show();
    }

    public void destCardsDialog(){
        final Dialog dialog = new Dialog(GamePlayActivity.this);
        dialog.setTitle(R.string.your_dest);
        dialog.setContentView(R.layout.dialog_dest_cards);
        TextView destinations = (TextView) dialog.findViewById(R.id.dest_list);
        StringBuilder sb = new StringBuilder();
        for (DestCard d : hand.getDestinationCards()){
            sb.append(d.getCity1().getName());
            sb.append(" to " + d.getCity2().getName());
            sb.append(": " + d.getPointsWorth() + "points\n");
        }
        destinations.setText(sb.toString());
        Button closeButton = (Button) dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.show();
    }
    public void setSelectedTrainCard(TextView view, int index){
        if (this.selectedTrainCard != null){
            this.selectedTrainCard.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        }
        this.selectedTrainCard = view;
        this.selectedTrainIndex = index;
        if (view != null) {
            view.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.cyan));
        }
    }

    public void trainCardsDialog(){
        dTrainCards.setTitle(R.string.draw_train_card);
        //dTrainCards.setContentView(R.layout.dialog_train_cards);
        final TextView tSlotTop = (TextView) dTrainCards.findViewById(R.id.t_card_top);
        Button takeCardButton = (Button) dTrainCards.findViewById(R.id.take_card_button);
        Button closeButton = (Button) dTrainCards.findViewById(R.id.close_button_3);
        TextView rulesText = (TextView) dTrainCards.findViewById(R.id.wild_rules_text);
        rulesText.setText(R.string.wild_rule);


        setSelectedTrainCard(null,-1);//Not sure if I really need to do this.
        tSlot0.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        tSlot1.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        tSlot2.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        tSlot3.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        tSlot4.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));
        tSlotTop.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.error));


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO figure out how to deal with this option when just one of the cards is taken.
                dTrainCards.hide();
            }
        });

        tSlot0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTrainCard(tSlot0,0);
            }
        });

        tSlot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTrainCard(tSlot1,1);
            }
        });

        tSlot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTrainCard(tSlot2,2);
            }
        });

        tSlot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTrainCard(tSlot3,3);
            }
        });

        tSlot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedTrainCard(tSlot4,4);
            }
        });

        tSlotTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO should the top of the deck be a certain index?
                setSelectedTrainCard(tSlotTop,5);
            }
        });

        takeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTrainIndex!= -1) {
                    presenter.takeTrainCards(selectedTrainIndex);
                }
                else{
                    toastMessage("You must select a card.");
                }
                //TODO closes dialog if it's the second card taken.
            }
        });

        dTrainCards.show();
    }


    public void drawMap(Map map){

        //Drawable mapDrawable = new (R.drawable.map);
        //mapView.setImageDrawable();
        //mapView.setImageResource(R.drawable.map);
        drawTracks(mapView,map.getTracks());
        mapView.setBackgroundResource(R.drawable.map);

        //TODO draw Map, Tracks, Cities
    }

    public void drawTracks(ImageView mapView,List<Track> tracks){
        Bitmap bmp = Bitmap.createBitmap(mapWindowWidth, mapWindowHeight, Bitmap.Config.ARGB_8888);
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.map);
        Canvas c = new Canvas(bmp);


        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (Track t : tracks){
            //Drawing underlying track (who owns it)
            if (t.getOwner() == -1) {
                p.setColor(getBoardColor(Color.WHITE));
            }
            else{
                p.setColor(getBoardColor(Color.values()[t.getOwner()]));
            }
                p.setStrokeWidth(8);
            PointF pt1 = t.getCity1().getLocation();
            PointF pt2 = t.getCity2().getLocation();
            if (t.getSecondTrack()) {
                pt1.offset(8,8);
                pt2.offset(8,8);
            }
                c.drawLine(pt1.x*mapScaleX,pt1.y*mapScaleY,
                        pt2.x*mapScaleX, pt2.y*mapScaleY, p);
            //Drawing the color of train required for the track.
            if (t.getColor() == null){
                p.setColor(android.graphics.Color.GRAY);
            }
            else {
                p.setColor(getBoardColor(t.getColor()));
            }
            p.setStrokeWidth(3);
            PointF p1 =t.getCity1().getLocation();
            PointF p2 =t.getCity2().getLocation();
            c.drawLine(p1.x*mapScaleX,p1.y*mapScaleY,
                    p2.x*mapScaleX, p2.y*mapScaleY, p);


            p.setColor(getBoardColor(Color.WHITE));
            p.setTextSize(20);
            PointF midpoint = midPoint(t.getCity1().getLocation(),t.getCity2().getLocation());
            c.drawText(Integer.toString(t.getLength()),midpoint.x,midpoint.y, p);
        }
        mapView.setImageBitmap(bmp);
        //mapView.draw(c);
        //mapView.setImageBitmap(bmp);
    }

    public PointF midPoint(PointF p1, PointF p2){
        float x = ((p1.x + p2.x)/2.0f)*mapScaleX;
        float y = ((p1.y + p2.y)/2.0f)*mapScaleY;
        return new PointF(x,y);
    }

    private int getResourceID(String resource) {
        Context context = getApplicationContext();
        return context.getResources().getIdentifier(resource, "drawable", context.getPackageName());
    }
    private String getFileName(DestCard card) {
        String rawName = card.getCity1().getName() + "," + card.getCity2().getName();
        return rawName.toLowerCase().replace(' ','_').replace(',','_').replaceAll("[()']","");
    }
    private int getCityFileId(DestCard card) {
        return getResourceID(getFileName(card));
    }

    public void initHandDialog(DrawnDestCards drawnCards){
        final Dialog dialog = new Dialog(GamePlayActivity.this);

        dialog.setContentView(R.layout.dialog_init_hand2);
        ImageButton slot0 = (ImageButton) dialog.findViewById(R.id.dest_card_0) ;
        ImageButton slot1 = (ImageButton) dialog.findViewById(R.id.dest_card_1);
        ImageButton slot2 = (ImageButton) dialog.findViewById(R.id.dest_card_2);
        ImageButton none = (ImageButton) dialog.findViewById(R.id.dest_card_none);
        Button returnCards = (Button) dialog.findViewById(R.id.return_cards_button);
        if (drawnCards == null){
            dialog.hide();
            return;
        }
        List<DestCard> cards = drawnCards.getRemainingDestCards();
        if (cards.size()== 3) {
            slot0.setImageResource(getCityFileId(cards.get(0)));
            slot1.setImageResource(getCityFileId(cards.get(1)));
            slot2.setImageResource(getCityFileId(cards.get(2)));
            final TextView text0 = (TextView) dialog.findViewById(R.id.dest_text_0);
            final TextView text1 = (TextView) dialog.findViewById(R.id.dest_text_1);
            final TextView text2 = (TextView) dialog.findViewById(R.id.dest_text_2);
            final TextView textNone = (TextView) dialog.findViewById(R.id.dest_text_none);
            text0.setText(drawnCards.getRemainingDestCards().get(0).toString());
            text1.setText(drawnCards.getRemainingDestCards().get(1).toString());
            text2.setText(drawnCards.getRemainingDestCards().get(2).toString());
            textNone.setText("Keep all cards");

//TODO set up Dest Cards Text
            slot0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(text0, 0);
                }
            });

            slot1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(text1, 1);
                }
            });

            slot2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(text2, 2);
                }
            });

            none.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(textNone, -1);
                }
            });
        }
        returnCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                returnInitDestCards();
            }
        });
        dialog.getWindow().setLayout(1400, 1000);
        dialog.setTitle(R.string.return_cards_title);
        dialog.show();
        System.out.println("showing Dialog");
    }

    public void updatePlayers(List<PlayerOverview> players){
        this.players = players;
        String username = ClientModelFacade.SINGLETON.getUser().getUsername().getString();

        View color = findViewById(R.id.player_color);
        TextView name = (TextView) findViewById(R.id.player_name);
        TextView points = (TextView) findViewById(R.id.player_points);
        TextView trains = (TextView) findViewById(R.id.player_trains_remaining);
        if(players != null) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(username)) {
                    color.setBackgroundColor(getBoardColor(players.get(i).getColor()));
                    name.setText(players.get(i).getUsername());
                    points.setText("Points: " + Integer.toString(players.get(i).getPoints()));
                    trains.setText("Pieces: " + Integer.toString(players.get(i).getNumPieces()));
                }
            }
        }
    }

    public void dialogLeaderBoard(){
        final Dialog dialog = new Dialog(GamePlayActivity.this);
        dialog.setContentView(R.layout.dialog_leaderboard);
        dialog.setTitle("Leaderboard");

        if (players == null){
            dialog.hide();
            return;
        }
        View color0 = dialog.findViewById(R.id.player_color0);
        TextView name0 = (TextView) dialog.findViewById(R.id.player_name0);
        TextView points0 = (TextView) dialog.findViewById(R.id.player_points0);
        TextView trains0 = (TextView) dialog.findViewById(R.id.player_trains_remaining0);

        color0.setBackgroundColor(getBoardColor(players.get(0).getColor()));
        name0.setText(players.get(0).getUsername());
        points0.setText("Points: " + Integer.toString(players.get(0).getPoints()));
        trains0.setText("Pieces: " + Integer.toString(players.get(0).getNumPieces()));

        View color1 = dialog.findViewById(R.id.player_color1);
        TextView name1 = (TextView) dialog.findViewById(R.id.player_name1);
        TextView points1 = (TextView) dialog.findViewById(R.id.player_points1);
        TextView trains1 = (TextView) dialog.findViewById(R.id.player_trains_remaining1);

        color1.setBackgroundColor(getBoardColor(players.get(1).getColor()));
        name1.setText(players.get(1).getUsername());
        points1.setText("Points: " + Integer.toString(players.get(1).getPoints()));
        trains1.setText("Pieces: " + Integer.toString(players.get(1).getNumPieces()));

        if (players.size() > 2) {

            View color2 = dialog.findViewById(R.id.player_color2);
            TextView name2 = (TextView) dialog.findViewById(R.id.player_name2);
            TextView points2 = (TextView) dialog.findViewById(R.id.player_points2);
            TextView trains2 = (TextView) dialog.findViewById(R.id.player_trains_remaining2);

            color2.setBackgroundColor(getBoardColor(players.get(2).getColor()));
            name2.setText(players.get(2).getUsername());
            points2.setText("Points: " + Integer.toString(players.get(2).getPoints()));
            trains2.setText("Pieces: " + Integer.toString(players.get(2).getNumPieces()));
        }
        if (players.size() > 3) {
            View color3 = dialog.findViewById(R.id.player_color3);
            TextView name3 = (TextView) dialog.findViewById(R.id.player_name3);
            TextView points3 = (TextView) dialog.findViewById(R.id.player_points3);
            TextView trains3 = (TextView) dialog.findViewById(R.id.player_trains_remaining3);

            color3.setBackgroundColor(getBoardColor(players.get(3).getColor()));
            name3.setText(players.get(3).getUsername());
            points3.setText("Points: " + Integer.toString(players.get(3).getPoints()));
            trains3.setText("Pieces: " + Integer.toString(players.get(3).getNumPieces()));
        }
        if (players.size()> 4) {
            View color4 = findViewById(R.id.player_color4);
            TextView name4 = (TextView) dialog.findViewById(R.id.player_name4);
            TextView points4 = (TextView) dialog.findViewById(R.id.player_points4);
            TextView trains4 = (TextView) dialog.findViewById(R.id.player_trains_remaining4);

            color4.setBackgroundColor(getBoardColor(players.get(4).getColor()));
            name4.setText(players.get(4).getUsername());
            points4.setText("Points: " + Integer.toString(players.get(4).getPoints()));
            trains4.setText("Pieces: " + Integer.toString(players.get(4).getNumPieces()));
        }

        Button closeButton = (Button) dialog.findViewById(R.id.close_button2);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }
    public void updateHand(Hand hand){
        this.hand = hand;
        updateTrainCards(hand.getTrainCards());
        updateDestCards(hand.getDestinationCards());
    }

    public void tracksDialog(){

    }

    public void drawDestCardsDialog(DrawnDestCards drawnCards){
        final Dialog dialog = new Dialog(GamePlayActivity.this);
        dialog.setContentView(R.layout.dialog_init_hand2);
        ImageButton slot0 = (ImageButton) dialog.findViewById(R.id.dest_card_0) ;
        ImageButton slot1 = (ImageButton) dialog.findViewById(R.id.dest_card_1);
        ImageButton slot2 = (ImageButton) dialog.findViewById(R.id.dest_card_2);
        ImageButton none = (ImageButton) dialog.findViewById(R.id.dest_card_none);
        Button returnCards = (Button) dialog.findViewById(R.id.return_cards_button);

        if (drawnCards == null){
            dialog.hide();
            return;
        }

        List<DestCard> cards = drawnCards.getRemainingDestCards();
        if (cards.size()== 3) {
            slot0.setImageResource(getCityFileId(cards.get(0)));
            slot1.setImageResource(getCityFileId(cards.get(1)));
            slot2.setImageResource(getCityFileId(cards.get(2)));
            final TextView text0 = (TextView) dialog.findViewById(R.id.dest_text_0);
            final TextView text1 = (TextView) dialog.findViewById(R.id.dest_text_1);
            final TextView text2 = (TextView) dialog.findViewById(R.id.dest_text_2);
            final TextView textNone = (TextView) dialog.findViewById(R.id.dest_text_none);
            text0.setText(drawnCards.getRemainingDestCards().get(0).toString());
            text1.setText(drawnCards.getRemainingDestCards().get(1).toString());
            text2.setText(drawnCards.getRemainingDestCards().get(2).toString());
            textNone.setText("Keep all cards");

//TODO set up Dest Cards Text
            slot0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedDestCard(text0, 0);
                }
            });

            slot1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedDestCard(text1, 1);
                }
            });

            slot2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedDestCard(text2, 2);
                }
            });

            none.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelectedDestCard(textNone, -1);
                }
            });
        }
        returnCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                returnDestCards();
            }
        });
        dialog.getWindow().setLayout(1400, 1000);
        dialog.setTitle(R.string.return_cards_title);
        dialog.show();

    }

    public void updateBank(TrainCard[] bankCards){
        View slot0 =  findViewById(R.id.card_slot_0);
        View slot1 =  findViewById(R.id.card_slot_1);
        View slot2 =  findViewById(R.id.card_slot_2);
        View slot3 =  findViewById(R.id.card_slot_3);
        View slot4 =  findViewById(R.id.card_slot_4);

        slot0.setBackgroundColor(getBoardColor(bankCards[0].getColor()));
        slot1.setBackgroundColor(getBoardColor(bankCards[1].getColor()));
        slot2.setBackgroundColor(getBoardColor(bankCards[2].getColor()));
        slot3.setBackgroundColor(getBoardColor(bankCards[3].getColor()));
        slot4.setBackgroundColor(getBoardColor(bankCards[4].getColor()));

        tSlot0.setBackgroundColor(getBoardColor(bankCards[0].getColor()));
        tSlot1.setBackgroundColor(getBoardColor(bankCards[1].getColor()));
        tSlot2.setBackgroundColor(getBoardColor(bankCards[2].getColor()));
        tSlot3.setBackgroundColor(getBoardColor(bankCards[3].getColor()));
        tSlot4.setBackgroundColor(getBoardColor(bankCards[4].getColor()));
    }

    public int getBoardColor(Color t){
        Resources res = getResources();
        if (t == Color.RED){
            return ContextCompat.getColor(getBaseContext(),R.color.card_red);
        }
        else if (t == Color.ORANGE){
            return ContextCompat.getColor(getBaseContext(),R.color.card_orange);
        }
        else if (t == Color.YELLOW){
            return ContextCompat.getColor(getBaseContext(),R.color.card_yellow);
        }
        else if (t == Color.GREEN){
            return ContextCompat.getColor(getBaseContext(),R.color.card_green);
        }
        else if (t == Color.BLUE){
            return ContextCompat.getColor(getBaseContext(),R.color.card_blue);
        }
        else if (t == Color.PURPLE){
            return ContextCompat.getColor(getBaseContext(),R.color.card_pink);
        }
        else if (t == Color.WILD){
            return ContextCompat.getColor(getBaseContext(),R.color.card_wild);
        }
        else if (t == Color.BLACK){
            return ContextCompat.getColor(getBaseContext(),R.color.card_black);
        }
        else if (t == Color.WHITE){
           return ContextCompat.getColor(getBaseContext(),R.color.card_white);
        }
        else{
            return ContextCompat.getColor(getBaseContext(),R.color.error);
        }
    }

    public void updateTrainCards(List<TrainCard> trainCards){
        int red = 0;
        int orange= 0;
        int yellow = 0;
        int green = 0;
        int blue = 0;
        int white = 0;
        int black = 0;
        int pink = 0;
        int wild = 0;

        for (TrainCard t : trainCards) {
            if (t.getColor() == Color.RED) {
                red++;
            } else if (t.getColor() == Color.ORANGE) {
                orange++;
            } else if (t.getColor() == Color.YELLOW) {
                yellow++;
            } else if (t.getColor() == Color.GREEN) {
                green++;
            } else if (t.getColor() == Color.BLUE) {
                blue++;
            } else if (t.getColor() == Color.PURPLE) {
                pink++;
            } else if (t.getColor() == Color.WILD) {
                wild++;
            } else if (t.getColor() == Color.BLACK) {
                black++;
            } else if (t.getColor() == Color.WHITE) {
                white++;
            }
        }
            TextView redText = (TextView) findViewById(R.id.red_card_num);
            TextView orangeText = (TextView) findViewById(R.id.orange_card_num);
            TextView yellowText = (TextView) findViewById(R.id.yellow_card_num);
            TextView greenText = (TextView) findViewById(R.id.green_card_num);
            TextView blueText = (TextView) findViewById(R.id.blue_card_num);
            TextView pinkText = (TextView) findViewById(R.id.pink_card_num);
            TextView wildText = (TextView) findViewById(R.id.wild_card_num);
            TextView blackText = (TextView) findViewById(R.id.black_card_num);
            TextView whiteText = (TextView) findViewById(R.id.white_card_num);

            redText.setText(Integer.toString(red));
            orangeText.setText(Integer.toString(orange));
            yellowText.setText(Integer.toString(yellow));
            greenText.setText(Integer.toString(green));
            blueText.setText(Integer.toString(blue));
            pinkText.setText(Integer.toString(pink));
            wildText.setText(Integer.toString(wild));
            blackText.setText(Integer.toString(black));
            whiteText.setText(Integer.toString(white));


    }

    public void updateDestCards(List<DestCard> destCards){

    }
    public void updateTurn(int player){
        if (getSupportActionBar() != null) {
           // getSupportActionBar().setTitle(players.get(player).getUsername() + "\'s Turn");
        }
    }


    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_draw_train:{
                presenter.clickTrainCards();
                //trainCardsDialog();
                break;
            }
            case R.id.menu_draw_dest:{
                presenter.clickDestCards();
                //drawDestCardsDialog(new DrawnDestCards());
                break;
            }
            case R.id.menu_lay_track:{
                presenter.clickTracks();

                break;
            }
            case R.id.menu_chat:{
                chatDialog();
                break;
            }
        }

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mDrawerToggle.syncState();
    }

    public void onEndGame(){
        //Intent intent = new Intent(getBaseContext(), EndGameActivity.class);
        //startActivity(intent);
    }


    }

