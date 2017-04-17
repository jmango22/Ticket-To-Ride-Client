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
    private int toastPopper;

    public CommandPoller(){
        toastPopper = 2000;
        timer = new Timer();
        timer.schedule(new commandpollerTask(), 0, 1000);

    }
    public void restart() {
        stopPoller();
        timer = new Timer();
        timer.schedule(new commandpollerTask(), 0, 1000);
    }

    public void stopPoller(){
        timer.cancel();
        timer.purge();
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
                    }else{
                        popFailToast();
                    }
                }
            });
        }
    }

    private void popFailToast(){
       toastPopper--;
        if(toastPopper == 0){
            ClientModelFacade.SINGLETON.getState().showToast("Server unavailable");
            toastPopper = 2000;
        }
    }
}
