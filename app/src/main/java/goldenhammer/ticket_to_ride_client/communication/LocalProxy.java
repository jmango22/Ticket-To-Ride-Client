package goldenhammer.ticket_to_ride_client.communication;

import android.graphics.PointF;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import goldenhammer.ticket_to_ride_client.model.City;
import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;
import goldenhammer.ticket_to_ride_client.model.Color;
import goldenhammer.ticket_to_ride_client.model.DestCard;
import goldenhammer.ticket_to_ride_client.model.GameList;
import goldenhammer.ticket_to_ride_client.model.GameListItem;
import goldenhammer.ticket_to_ride_client.model.GameModel;
import goldenhammer.ticket_to_ride_client.model.Hand;
import goldenhammer.ticket_to_ride_client.model.Player;
import goldenhammer.ticket_to_ride_client.model.PlayerOverview;
import goldenhammer.ticket_to_ride_client.model.Track;
import goldenhammer.ticket_to_ride_client.model.TrainCard;
import goldenhammer.ticket_to_ride_client.model.commands.Command;
import goldenhammer.ticket_to_ride_client.model.GameName;
import goldenhammer.ticket_to_ride_client.model.Password;
import goldenhammer.ticket_to_ride_client.model.Username;

public class LocalProxy implements IProxy {
    public static final LocalProxy SINGLETON = new LocalProxy();
    ClientModelFacade cmf = ClientModelFacade.SINGLETON;
    private LocalProxy() {
    }

    public void setUpFakeModel(){


    }

    @Override
    public void login(Username username, Password password, String serverHost, String serverPort, Callback c) {
        cmf.setUser(new Player(username,password));
    }

    @Override
    public void register(Username username, Password password, String serverHost, String serverPort, Callback c) {
        cmf.setUser(new Player(username,password));
    }

    @Override
    public void getPlayerGames(Callback c) {
        ArrayList<GameListItem> games = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();
        players.add("bob");
        players.add("jim");
        players.add(cmf.getUser().getUsername().getString());
        games.add(new GameListItem("test1",false,players));
        games.add(new GameListItem("test2",false,players));
        cmf.setMyGames(new GameList(games));
    }

    @Override
    public void getAllGames(Callback c) {
        ArrayList<GameListItem> games = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();
        players.add("bob");
        players.add("jim");
        games.add(new GameListItem("test3",false,players));
        games.add(new GameListItem("test4",false,players));
        cmf.setMyGames(new GameList(games));
    }

    @Override
    public void createGame(GameName gameName, Callback c) {
        GameList g = cmf.getMyGames();
        ArrayList<String> players = new ArrayList<>();
        players.add(cmf.getUser().getUsername().getString());
        g.addGame(new GameListItem(gameName.getString(),false, players));

    }

    @Override
    public void joinGame(GameName gameName, Callback c) {
        for (GameListItem g : cmf.getAvailableGames().getAllGames()){
            if (g.getName().equals(gameName.getString())){
                g.getPlayers().add(cmf.getUser().getUsername().getString());
            }
        }
    }

    @Override
    public void playGame(GameName gameName, Callback c) {
        /*GameModel gm = new GameModel();
        ArrayList<City> cities = new ArrayList<>();
        City c1 = new City(new PointF(10,20),"testCity");
        City c2 = new City(new PointF(20,30),"Babylon");
        cities.add(c1);
        cities.add(c1);
        gm.setCities(cities);
        ArrayList<PlayerOverview> leaderboard = new ArrayList<>();
        PlayerOverview p = new PlayerOverview(Color.BLACK,42,6,0,41);
        p.setUsername(cmf.getUser().getUsername().getString());
        PlayerOverview p1 = new PlayerOverview(Color.BLUE,43,7,0,47);
        p1.setUsername("mannequin");
        leaderboard.add(p);
        leaderboard.add(p1);
        gm.setLeaderBoard(leaderboard);
        ArrayList<Track> tracks = new ArrayList<>();
        Track t = new Track(c1,c2, 3,Color.PURPLE,0,false,c1.getLocation(),c2.getLocation());
        tracks.add(t);
        gm.setTracks(tracks);
        cmf.setCurrentGame(gm);*/
        //Add fake hand
        List<DestCard> destCards = new ArrayList<>();
        List<City> cities = cmf.getAllCities();
        DestCard d1 = new DestCard(cities.get(0), cities.get(1), 11);
        DestCard d2 = new DestCard(cities.get(1),cities.get(2), 22);
        DestCard d3 = new DestCard(cities.get(0), cities.get(2), 33);
        destCards.add(d1);
        destCards.add(d2);
        destCards.add(d3);

        List<TrainCard> handCards = new ArrayList<>();
        TrainCard tr1 = new TrainCard(Color.PURPLE);
        TrainCard tr2 = new TrainCard(Color.BLACK);
        TrainCard tr3 = new TrainCard(Color.WILD);
        TrainCard tr4 = new TrainCard(Color.BLUE);
        TrainCard tr5 = new TrainCard(Color.YELLOW);
        handCards.add(tr1);
        handCards.add(tr2);
        handCards.add(tr3);
        handCards.add(tr4);
        handCards.add(tr5);
        //Hand hand = new Hand(destCards,handCards);
        cmf.drawTrainCards(handCards);
        cmf.setDrawnDestCards(destCards);
        cmf.moveDrawnDestCardsToHand(new ArrayList<DestCard>());
        cmf.getUserDestCards();
        //Add fake bank
        List<TrainCard> trainCards = new ArrayList<>();
        TrainCard t1 = new TrainCard(Color.PURPLE);
        TrainCard t2 = new TrainCard(Color.BLACK);
        TrainCard t3 = new TrainCard(Color.WILD);
        TrainCard t4 = new TrainCard(Color.BLUE);
        TrainCard t5 = new TrainCard(Color.YELLOW);
        trainCards.add(t1);
        trainCards.add(t2);
        trainCards.add(t3);
        trainCards.add(t4);
        trainCards.add(t5);
        TrainCard[] tCards = {t1,t2,t3,t4,t5};
        cmf.setBankCards(tCards);

        Random r = new Random();
        cmf.claimTrack(cmf.getAllTracks().get(r.nextInt(cmf.getAllTracks().size())),1);
        cmf.claimTrack(cmf.getAllTracks().get(2),0);
    }

    @Override
    public void leaveGame(GameName gameName, Callback c) {

    }

    @Override
    public void doCommand(Command command, Callback c) {


    }

    @Override
    public void getCommands(int lastCommandNumber, Callback c) {

    }


}
