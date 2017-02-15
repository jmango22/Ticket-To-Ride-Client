package goldenhammer.ticket_to_ride_client.ui.login.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.model.GameListItem;

/**
 * Created by jon on 2/14/17.
 */

public class AvailableGameListAdapter extends RecyclerView.Adapter<AvailableGameListHolder>{

    private Context context;
    private ArrayList<GameListItem> availableGameList;

    public AvailableGameListAdapter(Context context, ArrayList<GameListItem> availableGameList){
        this.context = context;
        this.availableGameList = availableGameList;
    }

    @Override
    public AvailableGameListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
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