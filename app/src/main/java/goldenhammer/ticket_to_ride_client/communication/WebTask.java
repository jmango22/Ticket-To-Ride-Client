package goldenhammer.ticket_to_ride_client.communication;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by McKean on 2/15/2017.
 */

public class WebTask extends AsyncTask<Void, Void, String> {
    private String postData;
    private ClientCommunicator caller;
    private String urlText;
    private String gameName;
    private String

    public WebTask(String postData, String url, ClientCommunicator clientCommunicator){
        this.postData = postData;
        this.urlText = url;
        caller = clientCommunicator;

    }



    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            caller.setHeader(connection, gameName);
            connection.setDoOutput(true);
            caller.setHeader(connection, gameName);
            OutputStream send = connection.getOutputStream();
            caller.output(send, body);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                caller.setResults(connection.getInputStream());
                return true;
            }
            else{
                return false;
            }
        }catch(MalformedURLException e){
            return false;
        }catch (IOException e){
            return false;
        }
    }
}
