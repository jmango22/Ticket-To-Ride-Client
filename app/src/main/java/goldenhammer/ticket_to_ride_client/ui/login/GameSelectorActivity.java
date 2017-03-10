package goldenhammer.ticket_to_ride_client.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.R;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.GameListItem;
import goldenhammer.ticket_to_ride_client.ui.login.recycler.AllGameFragment;
import goldenhammer.ticket_to_ride_client.ui.login.recycler.MyGameFragment;
import goldenhammer.ticket_to_ride_client.ui.play.GamePlayActivity;

public class GameSelectorActivity extends AppCompatActivity {

    private static List<GameListItem> myGameList = new ArrayList<>();
    private static List<GameListItem> availableGameList = new ArrayList<>();

    private static AvailableGamesPresenter availableGamesPresenter;
    private static MyGamesPresenter myGamesPresenter;

    private MyGameFragment myGameFragment = new MyGameFragment();
    private AllGameFragment allGameFragment = new AllGameFragment();


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);

        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

        availableGamesPresenter = new AvailableGamesPresenter(this);
        myGamesPresenter = new MyGamesPresenter(this);

        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText gameName = (EditText) findViewById(R.id.gameNameEditText);
                myGamesPresenter.createGame(gameName.getText().toString());
                gameName.setText("");
                update();
            }
        });

        ServerProxy.SINGLETON.startGameListPolling();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //This is where I return the Fragments with the correct recycler views
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return the correct Fragment
            switch (position) {
                case 0:
                    return allGameFragment;
                case 1:
                    return myGameFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All Games";
                case 1:
                    return "My Games";
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public void toastMessage(String message) {
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setMyGameList(List<GameListItem> myGameList) {
        this.myGameList = myGameList;
    }

    public void setAvailableGameList(List<GameListItem> availableGameList) {
        this.availableGameList = availableGameList;
    }

    public void onPlayGame() {

        Intent intent = new Intent(getBaseContext(), GamePlayActivity.class);
        startActivity(intent);
    }


    public static List<GameListItem> getAvailableGameList() {
        return availableGameList;
    }

    public static List<GameListItem> getMyGameList() {
        return myGameList;
    }

    public static MyGamesPresenter getMyGamesPresenter() {
        return myGamesPresenter;
    }

    public static AvailableGamesPresenter getAvailableGamesPresenter() {
        return availableGamesPresenter;
    }

    public void runOnThisThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }

    public void update() {
        System.out.println("Updating...");
        allGameFragment.update();
        myGameFragment.update();

        if(mSectionsPagerAdapter != null ) {
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        ServerProxy.SINGLETON.stopGameListPolling();
        availableGamesPresenter.onPause();
        myGamesPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServerProxy.SINGLETON.startGameListPolling();
        availableGamesPresenter.onResume();
        myGamesPresenter.onResume();
    }
}