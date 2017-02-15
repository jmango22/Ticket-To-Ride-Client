package goldenhammer.ticket_to_ride_client.ui.login.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.GameListItem;

/**
 * Created by jon on 2/14/17.
 */

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
                //myGamesPresenter.joinGame(gameListItem.getName());
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
