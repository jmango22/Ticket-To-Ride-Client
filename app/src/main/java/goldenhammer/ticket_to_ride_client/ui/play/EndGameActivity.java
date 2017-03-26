package goldenhammer.ticket_to_ride_client.ui.play;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.PlayerOverview;

public class EndGameActivity extends AppCompatActivity {
    EndGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        presenter = new EndGamePresenter(this);
        presenter.setInfo();
    }

}
