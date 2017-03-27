package goldenhammer.ticket_to_ride_client.ui.play;


import android.content.Intent;
import android.content.res.Resources;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.EndResult;
import goldenhammer.ticket_to_ride_client.model.GameModel;
import goldenhammer.ticket_to_ride_client.model.PlayerOverview;
import goldenhammer.ticket_to_ride_client.ui.login.GameSelectorActivity;

import static java.security.AccessController.getContext;

/**
 * Created by rache on 3/24/2017.
 */

public class EndGamePresenter implements IEndGamePresenter {
    EndGameActivity owner;
    ClientModelFacade model;
    EndGamePresenter(EndGameActivity owner){
        this.owner = owner;
        this.model = ClientModelFacade.SINGLETON;
    }
    @Override
    public void setInfo() {
        Resources res = owner.getResources();
        ArrayList<EndResult> results = model.getEndResults();
        for(int i = 0; i < results.size();i++) {
            String person = "endGame_player" + i;
            int id = res.getIdentifier(person + "_name", "id", owner.getPackageName());
            TextView name = (TextView)owner.findViewById(id);
            name.setText(inttoName(results.get(i).getPlayer()));

            id = res.getIdentifier(person + "_builtTrains", "id", owner.getPackageName());
            TextView builtTrains = (TextView)owner.findViewById(id);
            builtTrains.setText(Integer.toString(results.get(i).getBuiltTrainPoints()));

            id = res.getIdentifier(person + "_completedDest", "id",owner.getPackageName());
            TextView completedDest = (TextView)owner.findViewById(id);
            completedDest.setText(Integer.toString(results.get(i).getCompletedDestinations()));

            id = res.getIdentifier(person + "_incomplete", "id", owner.getPackageName());
            TextView incompleteDest = (TextView)owner.findViewById(id);
            incompleteDest.setText(Integer.toString(results.get(i).getIncompleteDestinations()));

            id = res.getIdentifier(person + "_longestCont", "id", owner.getPackageName());
            TextView longestCont = (TextView)owner.findViewById(id);
            longestCont.setText(Integer.toString(results.get(i).getLongestContinuousTrain()));

            id = res.getIdentifier(person + "_total", "id", owner.getPackageName());
            TextView total = (TextView)owner.findViewById(id);
            total.setText(Integer.toString(results.get(i).getTotal()));
        }
        calculateWinner();
    }

    private void calculateWinner(){
        ArrayList<EndResult> players = model.getEndResults();
        EndResult winner = players.get(0);
        for(EndResult p: players){
            if(p.getTotal() > winner.getTotal()){
                winner = p;
            }
        }
        TextView winText =  (TextView)owner.findViewById(R.id.endGame_Winner);
        String winnerText = "The Winner is " + inttoName(winner.getPlayer()) + "!!!!";
        winText.setText(winnerText.toUpperCase());
    }

    private String inttoName(int number){
        PlayerOverview player =  ClientModelFacade.SINGLETON.getCurrentGame().getLeaderBoard().get(number);
        return player.getUsername();
    }

    @Override
    public void buttonClicked() {
        Intent intent = new Intent(owner.getApplicationContext(), GameSelectorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        ClientModelFacade.SINGLETON.setCurrentGame(new GameModel());
        owner.startActivity(intent);
    }
}
