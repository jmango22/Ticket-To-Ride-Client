package goldenhammer.ticket_to_ride_client.ui;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.GameListItem;
import goldenhammer.ticket_to_ride_client.model.GameName;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;


public class GameSelectorActivity extends AppCompatActivity {

    //TODO : toastMessage method to make toast messages
    //TODO : setAvailableGames that takes a List of All Games
    //TODO : setMyGames that takes a List of My Games

    RecyclerView allGames = (RecyclerView)findViewById(R.id.allGamesRecycler);
    RecyclerView myGames = (RecyclerView)findViewById(R.id.myGamesRecycler);
    MyGamesPresenter myGamesPresenter = new MyGamesPresenter(this);
    AvailableGamesPresenter availableGamesPresenter = new AvailableGamesPresenter(this);
    LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());

    public void toastMessage(String message) {
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);

        allGames.setLayoutManager(llm);
        myGames.setLayoutManager(llm);
    }


    ////////////////////////////////
    public class GameListAdapter extends RecyclerView.Adapter<MyGameListItemHolder>{

        @Override
        public MyGameListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(MyGameListItemHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


    public class MyGameListItemHolder extends RecyclerView.ViewHolder {
        private TextView gameName;
        private TextView players;
        private Button leaveButton;
        private Button playButton;

        public MyGameListItemHolder(final View itemView) {
            super(itemView);
            gameName = (TextView) itemView.findViewById(R.id.gameNameText);
            players = (TextView) itemView.findViewById(R.id.playerNamesText);
            leaveButton = (Button) itemView.findViewById(R.id.leaveButton);
            playButton = (Button) itemView.findViewById(R.id.playButton);


            leaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesPresenter.leaveGame(gameName.getText().toString());
                }
            });

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGamesPresenter.playGame(gameName.getText().toString());
                }
            });
        }
    }
}
