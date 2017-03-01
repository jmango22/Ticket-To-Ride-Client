package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import goldenhammer.ticket_to_ride_client.communication.IProxy;
import goldenhammer.ticket_to_ride_client.communication.ServerProxy;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by devonkinghorn on 2/22/17.
 */

public class GamePlayPresenter implements Observer, IGamePlayPresenter {
    private GamePlayActivity owner;
    private IProxy proxy;
    public GamePlayPresenter(GamePlayActivity activity) {
        owner = activity;
        proxy = ServerProxy.SINGLETON;
        ClientModelFacade.SINGLETON.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void takeTrainCard() {

    }

    @Override
    public void takeDestCard() {

    }

    @Override
    public void takeTrack() {

    }

    @Override
    public void loadGame() {

    }

    @Override
    public void update() {

    }

    public void demo() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hey");

//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println("jude");
//                    }
//                },1000);
            }
        },1000);
    }
}
