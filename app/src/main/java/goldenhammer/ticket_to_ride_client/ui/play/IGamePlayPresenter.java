package goldenhammer.ticket_to_ride_client.ui.play;

/**
 * Created by McKean on 2/22/2017.
 */

public interface IGamePlayPresenter {
  void takeTrainCard();
  void takeDestCard();
  void takeTrack();
  void loadGame();
  void update();
}
