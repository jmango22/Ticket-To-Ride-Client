package goldenhammer.ticket_to_ride_client.communication;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by rachel on 3/6/2017.
 */

public class CommandPoller {
    Timer timer;

    public CommandPoller(){
        timer = new Timer();
        timer.schedule(new commandpollerTask(), 0, 10000);

    }


    public class commandpollerTask extends TimerTask {
        @Override
        public void run() {
            int number = ClientModelFacade.SINGLETON.getLastCommandNumber();
            ServerProxy.SINGLETON.getCommands(number, new Callback() {
                @Override
                public void run(Results res) {
                    if(res.getResponseCode() == HttpURLConnection.HTTP_OK){
                        ClientModelFacade.SINGLETON.addCommands(Serializer.deserializeCommands(res.getBody()));
                    }
                }
            });
        }
    }
}
