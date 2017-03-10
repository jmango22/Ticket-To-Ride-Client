package goldenhammer.ticket_to_ride_client.communication;


import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by rache on 3/10/2017.
 */

public class MessagePoller {
    Timer timer;

    public MessagePoller() {
        timer = new Timer();
        timer.schedule(new messageTask(), 0, 1000);
    }

    public void restart() {
        stopPoller();
        timer = new Timer();
        timer.schedule(new messageTask(), 0, 1000);
    }

    public void stopPoller() {
        timer.cancel();
        timer.purge();
    }

    public class messageTask extends TimerTask {
        @Override
        public void run() {
            ServerProxy.SINGLETON.getMessages(new Callback() {
                @Override
                public void run(Results res) {
                    if (res.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        ClientModelFacade.SINGLETON.setMessages(Serializer.deserializeChat(res.getBody()));
                    }
                }
            });
        }
    }
}
