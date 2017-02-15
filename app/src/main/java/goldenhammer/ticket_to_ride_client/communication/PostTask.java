package goldenhammer.ticket_to_ride_client.communication;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by McKean on 2/15/2017.
 */

public class PostTask extends AsyncTask<Void, Void, String> {
    private JSONObject postData;
    private ClientCommunicator caller;
    private String urlText;
    private String authorizationToken;
    private String gameName;

    public PostTask(JSONObject postData, String url, ClientCommunicator clientCommunicator,
                    String authorizationToken,  String gameName){
        this.postData = postData;
        this.urlText = url;
        caller = clientCommunicator;
        this.authorizationToken = authorizationToken;
        this.gameName = gameName;

    }



    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            caller.setHeader(connection, gameName);
            OutputStream send = connection.getOutputStream();
            caller.output(send, postData);
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
            return e.getMessage();
        }
    }
}
