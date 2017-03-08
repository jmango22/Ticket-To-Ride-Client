package goldenhammer.ticket_to_ride_client.communication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by rache on 2/10/2017.
 */

public class GamePoller {
    Timer timer;

    public GamePoller(){
        timer = new Timer();
        timer.schedule(new pollerTask(), 0, 1000);
    }


    public class pollerTask extends TimerTask{
        @Override
        public void run() {
            ServerProxy.SINGLETON.getAllGames(new Callback() {
                @Override
                public void run(Results res) {
                    if (res.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        ClientModelFacade.SINGLETON.setAvailableGames(Serializer.deserializeGames(res.getBody()));
                    }
                }
            });
            ServerProxy.SINGLETON.getPlayerGames(new Callback() {
                @Override
                public void run(Results res) {
                    if(res.getResponseCode() == HttpURLConnection.HTTP_OK){
                        ClientModelFacade.SINGLETON.setMyGames(Serializer.deserializeGames(res.getBody()));
                    }
                }
            });
        }
    }
}
