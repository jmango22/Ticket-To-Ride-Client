package goldenhammer.ticket_to_ride_client.communication;

import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

class LocalProxy implements IProxy{
    public static final LocalProxy SINGLETON = new LocalProxy();

    private LocalProxy(){}

    @Override
    public void login(Username username, Password password, String serverHost, String serverPort, Callback c) {

    }

    @Override
    public void register(Username username, Password password, String serverHost, String serverPort, Callback c) {

    }

    @Override
    public void getPlayerGames(Callback c) {

    }

    @Override
    public void getAllGames(Callback c) {

    }

    @Override
    public void createGame(GameName gameName, Callback c) {

    }

    @Override
    public void joinGame(GameName gameName, Callback c) {

    }

    @Override
    public void playGame(GameName gameName, Callback c) {

    }

    @Override
    public void leaveGame(GameName gameName, Callback c) {

    }
}
