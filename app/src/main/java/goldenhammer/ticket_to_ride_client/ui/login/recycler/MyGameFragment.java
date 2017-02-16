package goldenhammer.ticket_to_ride_client.ui.login.recycler;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.ui.login.GameSelectorActivity;

import android.os.Bundle;

/**
 * Created by jon on 2/15/17.
 */

public class MyGameFragment extends Fragment {

    // Requires empty public constructor
    public MyGameFragment() {}

    private MyGameListAdapter myGameListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        RecyclerView myGames = (RecyclerView) rootView.findViewById(R.id.myRecycler);
         myGameListAdapter = new MyGameListAdapter(getContext(), GameSelectorActivity.getMyGameList());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        myGames.setLayoutManager(llm);
        myGames.setAdapter(myGameListAdapter);

        return rootView;
    }

    public void update(){
        myGameListAdapter.notifyDataSetChanged();
    }

}

