package goldenhammer.ticket_to_ride_client.communication;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rache on 2/10/2017.
 */

public class Poller {
    Timer timer;

    public Poller(){
        timer = new Timer();
        timer.schedule(new pollerTask(), 0, 5);
    }


    public class pollerTask extends TimerTask{
        @Override
        public void run() {
            ServerProxy.SINGLETON.getAllGames();
        }
    }
}
