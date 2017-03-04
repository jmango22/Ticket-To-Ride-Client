package goldenhammer.ticket_to_ride_client.ui.play;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public abstract class DoUpdate {
    private GamePlayPresenter presenter;
    public DoUpdate(GamePlayPresenter presenter) {
        this.presenter = presenter;
    }
    abstract public void update();
}
