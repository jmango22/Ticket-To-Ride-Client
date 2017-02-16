package goldenhammer.ticket_to_ride_client.ui.login.recycler;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.GameListItem;
import goldenhammer.ticket_to_ride_client.ui.login.GameSelectorActivity;
import goldenhammer.ticket_to_ride_client.ui.play.GameActivity;

import static java.security.AccessController.getContext;

/**
 * Created by jon on 2/14/17.
 */

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
                GameSelectorActivity.getMyGamesPresenter().leaveGame(gameListItem.getName());
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameListItem.getPlayers().size()>1) {
                    GameSelectorActivity.getMyGamesPresenter().playGame(gameListItem.getName());
                }
                else{
                    Toast.makeText(itemView.getContext(), "A game requires at least two players to play",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void bindGameListItem(GameListItem g){
        gameListItem = g;
        gameName.setText(g.getName());
        StringBuilder playerNames = new StringBuilder();
        for (String p: g.getPlayers()){
            playerNames.append(p);
            playerNames.append(" ");
        }
        players.setText(playerNames.toString());
    }
}