package goldenhammer.ticket_to_ride_client.ui;

import goldenhammer.ticket_to_ride_client.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameSelectorActivity extends AppCompatActivity {

    //TODO : toastMessage method to make toast messages
    //TODO : setAvailableGames that takes a List of All Games
    //TODO : setMyGames that takes a List of My Games

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);
    }
}
