package goldenhammer.ticket_to_ride_client.ui;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.GameListItem;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Player;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;


public class GameSelectorActivity extends AppCompatActivity {

    //TODO : toastMessage method to make toast messages
    //TODO : setAvailableGames that takes a List of All Games
    //TODO : setMyGames that takes a List of My Games
    ArrayList<GameListItem> myGameList;
    ArrayList<GameListItem> availableGameList;

    RecyclerView allGames = (RecyclerView)findViewById(R.id.allGamesRecycler);
    RecyclerView myGames = (RecyclerView)findViewById(R.id.myGamesRecycler);
    MyGamesPresenter myGamesPresenter = new MyGamesPresenter(this);
    AvailableGamesPresenter availableGamesPresenter = new AvailableGamesPresenter(this);
    LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);

        allGames.setLayoutManager(llm);
        myGames.setLayoutManager(llm);
    }

    public void toastMessage(String message) {
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setMyGameList(ArrayList<GameListItem> myGameList) {
        this.myGameList = myGameList;
    }

    public void setAvailableGameList(ArrayList<GameListItem> availableGameList) {
        this.availableGameList = availableGameList;
    }

    ////////////////////////////////
    public class MyGameListAdapter extends RecyclerView.Adapter<MyGameListItemHolder>{

        public MyGameListAdapter(){}

        @Override
        public MyGameListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GameSelectorActivity.this);
            View view = inflater.inflate(R.layout.my_game_card, null);
            return new MyGameListItemHolder(view);
        }

        @Override
        public void onBindViewHolder(MyGameListItemHolder holder, int position) {
            GameListItem g = myGameList.get(position);
            holder.bindGameListItem(g);
        }

        @Override
        public int getItemCount() {
            return myGameList.size();
        }
    }

///////////////////////////////////////////////////////////////////////////////////
    public class MyGameListItemHolder extends RecyclerView.ViewHolder {
        private TextView gameName;
        private TextView players;
        private Button leaveButton;
        private Button playButton;
        private GameListItem gameListItem;

        public MyGameListItemHolder(final View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.gameNameText);
            players = (TextView) itemView.findViewById(R.id.playerNamesText);
            leaveButton = (Button) itemView.findViewById(R.id.leaveButton);
            playButton = (Button) itemView.findViewById(R.id.playButton);


            leaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesPresenter.leaveGame(gameListItem.getName());
                }
            });

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesPresenter.playGame(gameListItem.getName());
                }
            });
        }

        public void bindGameListItem(GameListItem g){
            gameListItem = g;
            gameName.setText(g.getName());
            StringBuilder playerNames = new StringBuilder();
            for (Player p: g.getPlayers()){
                playerNames.append(p.getUsername().getString());
                playerNames.append(" ");
            }
            players.setText(playerNames.toString());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    public class AvailableGameListAdapter extends RecyclerView.Adapter<AvailableGameListHolder>{

        @Override
        public AvailableGameListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GameSelectorActivity.this);
            View view = inflater.inflate(R.layout.game_card, null);
            return new AvailableGameListHolder(view);
        }

        @Override
        public void onBindViewHolder(AvailableGameListHolder holder, int position) {
            GameListItem g = availableGameList.get(position);
            holder.bindGameListItem(g);
        }

        @Override
        public int getItemCount() {
            return availableGameList.size();
        }
    }





    ///////////////////////////////////////////////////////////////////////////////
    public class AvailableGameListHolder extends RecyclerView.ViewHolder{
        private TextView gameName;
        private TextView players;
        private Button joinButton;
        private GameListItem gameListItem;

        public AvailableGameListHolder(View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.gameNameText);
            players = (TextView) itemView.findViewById(R.id.playerNamesText);
            joinButton = (Button) itemView.findViewById(R.id.joinButton);

            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesPresenter.joinGame(gameListItem.getName());
                }
            });
        }

        public void bindGameListItem(GameListItem g){
            gameListItem = g;
            gameName.setText(g.getName());
            StringBuilder playerNames = new StringBuilder();
            for (Player p: g.getPlayers()){
                playerNames.append(p.getUsername().getString());
                playerNames.append(" ");
            }
            players.setText(playerNames.toString());
        }
    }
}
