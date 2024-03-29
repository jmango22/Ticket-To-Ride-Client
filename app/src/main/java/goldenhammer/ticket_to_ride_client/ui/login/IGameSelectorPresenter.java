package goldenhammer.ticket_to_ride_client.ui.login;

/**
 * Created by McKean on 2/11/2017.
 */

public interface IGameSelectorPresenter {

    public void joinGame(String gameName);

    public void playGame(String gameName);

    public void leaveGame(String gameName);
    void onResume();
    void onPause();
}
