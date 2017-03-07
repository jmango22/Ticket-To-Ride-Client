package goldenhammer.ticket_to_ride_client.ui.play;

import android.app.Dialog;
import android.provider.ContactsContract;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.model.Map;
import goldenhammer.ticket_to_ride_client.model.PlayerOverview;
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
    private GamePlayPresenter presenter;
    private List<DestCard> drawnDestCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        presenter = new GamePlayPresenter(this);
        initDrawer();
        //comment
    }

    private void selectItem(int position) {

            /*Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = new CreateFragment();
                    break;
                case 1:
                    fragment = new ReadFragment();
                    break;
                case 2:
                    fragment = new HelpFragment();
                    break;

                default:
                    break;
            }

            if (fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
*/
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //getActionBar().setTitle(mNavDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

            /*} else {
                Log.e("MainActivity", "Error in creating fragment");
            }*/
        }

    public void initDrawer(){
        ObjectDrawerItem[] drawerItems = new ObjectDrawerItem[4];
        drawerItems[0] = new ObjectDrawerItem(R.drawable.icon, "ItemToBe");
        DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.listview_item_row, drawerItems);
        mDrawerList.setAdapter(adapter);
        class DrawerItemClickListener implements ListView.OnItemClickListener {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        }
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                new Toolbar(getBaseContext()),//TODO not sure if this is right. Was R.drawable.icon
                R.string.drawer_open,
                R.string.drawer_closed
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    public List<DestCard> initializeHand(List<DestCard> drawnDestCards, Hand hand){
        updateHand(hand);
        this.drawnDestCards = drawnDestCards;
        initHandDialog(drawnDestCards);
        return null;
    }
    public void setSelected(TextView view, int index){
        if (this.selectedView != null){
            this.selectedView.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.card_black));
        }
        this.selectedView = view;
        this.selectedIndex = index;
        view.setTextColor(ContextCompat.getColor(getBaseContext(),R.color.card_yellow));
    }

    public void returnDestCards(){
        List<DestCard> toReturn = new ArrayList<>();
        if (selectedIndex != -1) {
            toReturn.add(drawnDestCards.get(selectedIndex));
        }
        //TODO presenter.returnDestCards(toReturn);
    }

    public void initHandDialog(List<DestCard> drawnCards){
        final Dialog dialog = new Dialog(GamePlayActivity.this);
        dialog.setTitle(R.string.return_cards_title);
        dialog.setContentView(R.layout.dialog_init_hand);
        ImageButton slot0 = (ImageButton) findViewById(R.id.dest_card_0) ;
        ImageButton slot1 = (ImageButton) findViewById(R.id.dest_card_1);
        ImageButton slot2 = (ImageButton) findViewById(R.id.dest_card_2);
        ImageButton none = (ImageButton) findViewById(R.id.dest_card_none);
        Button returnCards = (Button) findViewById(R.id.return_cards_button);

        final TextView text0 = (TextView) findViewById(R.id.dest_text_0);
        final TextView text1 = (TextView) findViewById(R.id.dest_text_1);
        final TextView text2 = (TextView) findViewById(R.id.dest_text_2);
        final TextView textNone = (TextView) findViewById(R.id.dest_text_none);
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

        returnCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                returnDestCards();
            }
        });

        dialog.show();
    }

    public void drawMap(Map map){
    //TODO draw Map, Tracks, Cities
    }

    public void updatePlayers(List<PlayerOverview> players){
        this.players = players;
        String username = ClientModelFacade.SINGLETON.getUser().getUsername().getString();
        if (username == players.get(0).getUsername()){

        }
        updateLeaderBoard();
    }

    public void updateLeaderBoard(){
        View color1 = findViewById(R.id.player1_color);
        TextView name1 = (TextView) findViewById(R.id.player1_name);
        TextView points1 = (TextView) findViewById(R.id.player1_points);
        TextView trains1 = (TextView) findViewById(R.id.player1_trains_remaining);

        View color2 = findViewById(R.id.player2_color);
        TextView name2= (TextView) findViewById(R.id.player2_name);
        TextView points2 = (TextView) findViewById(R.id.player2_points);
        TextView trains2= (TextView) findViewById(R.id.player2_trains_remaining);

        View color3 = findViewById(R.id.player3_color);
        TextView name3 = (TextView) findViewById(R.id.player3_name);
        TextView points3 = (TextView) findViewById(R.id.player3_points);
        TextView trains3 = (TextView) findViewById(R.id.player3_trains_remaining);

        View color4 = findViewById(R.id.player4_color);
        TextView name4 = (TextView) findViewById(R.id.player4_name);
        TextView points4 = (TextView) findViewById(R.id.player4_points);
        TextView trains4 = (TextView) findViewById(R.id.player4_trains_remaining);

        View color5 = findViewById(R.id.player5_color);
        TextView name5 = (TextView) findViewById(R.id.player5_name);
        TextView points5 = (TextView) findViewById(R.id.player5_points);
        TextView trains5 = (TextView) findViewById(R.id.player5_trains_remaining);
    }
    public void updateHand(Hand hand){
        this.hand = hand;
        updateTrainCards(hand.getTrainCards());
        updateDestCards(hand.getDestinationCards());
    }

    public void updateBank(TrainCard[] bankCards){
        View slot0 =  findViewById(R.id.card_slot_0);
        View slot1 =  findViewById(R.id.card_slot_1);
        View slot2 =  findViewById(R.id.card_slot_2);
        View slot3 =  findViewById(R.id.card_slot_3);
        View slot4 =  findViewById(R.id.card_slot_4);

        slot0.setBackgroundColor(getCardColor(bankCards[0]));
        slot1.setBackgroundColor(getCardColor(bankCards[1]));
        slot2.setBackgroundColor(getCardColor(bankCards[2]));
        slot3.setBackgroundColor(getCardColor(bankCards[3]));
        slot4.setBackgroundColor(getCardColor(bankCards[4]));
    }

    public int getCardColor(TrainCard t){
        if (t.getColor() == Color.RED){
            return R.color.card_red;
        }
        else if (t.getColor() == Color.ORANGE){
            return R.color.card_orange;
        }
        else if (t.getColor() == Color.YELLOW){
            return R.color.card_yellow;
        }
        else if (t.getColor() == Color.GREEN){
            return R.color.card_green;
        }
        else if (t.getColor() == Color.BLUE){
            return R.color.card_blue;
        }
        else if (t.getColor() == Color.PURPLE){
            return R.color.card_pink;
        }
        else if (t.getColor() == Color.WILD){
            return R.color.card_wild;
        }
        else if (t.getColor() == Color.BLACK){
            return R.color.card_black;
        }
        else if (t.getColor() == Color.WHITE){
           return R.color.card_white;
        }
        else{
            return R.color.error;
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
        getActionBar().setTitle(players.get(player).getUsername() + "\'s Turn");
    }


    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    }

