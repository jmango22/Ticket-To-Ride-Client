package goldenhammer.ticket_to_ride_client.communication;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rache on 2/15/2017.
 */

public class GetTask extends AsyncTask<Void, Void, String> {
    private ClientCommunicator caller;
    private String urlText;
    private String gameName;

    public GetTask(String url, String gameName, ClientCommunicator clientCommunicator) {
        this.urlText = url;
        caller = clientCommunicator;
        this.gameName = gameName;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            caller.setHeader(connection, gameName);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                caller.setResults(connection.getInputStream());
                return "Success!";
            }
            else{
                return "Unsuccessful. Please try again.";
            }
        }catch(MalformedURLException e){
            return "Wrong URL. Check Port and Host";
        }catch (IOException e){
            return "Something went wrong";
        }
    }

}