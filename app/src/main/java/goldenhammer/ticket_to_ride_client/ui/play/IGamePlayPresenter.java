package goldenhammer.ticket_to_ride_client.ui.play;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.Track;

/**
 * Created by McKean on 2/22/2017.
 */

public interface IGamePlayPresenter {
  void takeTrainCards(int index);
  void takeDestCards();
  void returnDestCards(List<DestCard> toReturn);
  void layTrack(Track track);
  void loadGame();
  void onPause();
  void onResume();
}
