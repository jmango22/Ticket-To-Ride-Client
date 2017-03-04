package goldenhammer.ticket_to_ride_client.ui.play;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class UpdateSelector {

    public static DoUpdate MustReturnDestCard(GamePlayPresenter presenter){
        return new DoUpdate(presenter) {
            @Override
            public void update() {
                //TODO: set the activity to show the presenter card
            }
        };
    }

    public static DoUpdate NotMyTurn(GamePlayPresenter presenter) {
        return new DoUpdate(presenter) {
            @Override
            public void update() {
                //TODO: only draw the tracks and update the scores/positions
            }
        };
    }
}
