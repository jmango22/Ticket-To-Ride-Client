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

public class MyGameListAdapter extends RecyclerView.Adapter<MyGameListItemHolder>{

    private Context context;

    private ArrayList<GameListItem> myGameList;

    public MyGameListAdapter(Context context, ArrayList<GameListItem> myGameList){
        this.context = context;
        this.myGameList = myGameList;
    }

    @Override
    public MyGameListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
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
