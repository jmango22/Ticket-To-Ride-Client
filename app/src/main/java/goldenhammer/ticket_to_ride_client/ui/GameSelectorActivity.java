package goldenhammer.ticket_to_ride_client.ui;

import goldenhammer.ticket_to_ride_client.R;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;


public class GameSelectorActivity extends AppCompatActivity {

    //TODO : toastMessage method to make toast messages
    //TODO : setAvailableGames that takes a List of All Games
    //TODO : setMyGames that takes a List of My Games

    RecyclerView allGames = (RecyclerView)findViewById(R.id.allGamesRecycler);
    RecyclerView myGames = (RecyclerView)findViewById(R.id.myGamesRecycler);

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


}
