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

public class AllGameFragment extends Fragment{

    // Requires empty public constructor
    public AllGameFragment() {}

    private AvailableGameListAdapter availableGameListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        RecyclerView allGames = (RecyclerView) rootView.findViewById(R.id.myRecycler);
        availableGameListAdapter = new AvailableGameListAdapter(getContext(), GameSelectorActivity.getAvailableGameList());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        allGames.setLayoutManager(llm);
        allGames.setAdapter(availableGameListAdapter);

        return rootView;
    }

    public void update(){
        availableGameListAdapter= new AvailableGameListAdapter(getContext(), GameSelectorActivity.getAvailableGameList());
    }

}
