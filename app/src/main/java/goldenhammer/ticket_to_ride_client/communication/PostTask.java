package goldenhammer.ticket_to_ride_client.communication;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import goldenhammer.ticket_to_ride_client.model.ClientModelFacade;

/**
 * Created by McKean on 2/15/2017.
 */

public class PostTask extends AsyncTask<Void, Void, Results> {
    private JSONObject postData;
    private ClientCommunicator caller;
    private String urlText;
    private Callback callback;

    public PostTask(JSONObject postData, String url, ClientCommunicator clientCommunicator, Callback callback){
        this.postData = postData;
        this.urlText = url;
        caller = clientCommunicator;
        this.callback = callback;
    }



    @Override
    protected Results doInBackground(Void... params) {
        try {
            URL url = new URL(urlText);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            caller.setHeader(connection);

            OutputStream send = connection.getOutputStream();
            caller.output(send, postData);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                return new Results(setResults(connection.getInputStream()), connection.getResponseCode());
            }
            else{
                String result = setResults(connection.getErrorStream());
                return new Results(result, connection.getResponseCode());
            }
        }catch(MalformedURLException e){
            return new Results("Wrong URL. Check Port and Host", 500);
        }catch (IOException e){
            return new Results(e.getMessage(), 600);
        }
    }

    @Override
    protected void onPostExecute(Results results){
        this.callback.run(results);
    }

    private String setResults(InputStream input){
        try {
            StringBuilder string = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine();
            while (line != null) {
                string.append(line);
                line = br.readLine();
            }
            return string.toString();
        }catch (IOException e){
        }
        return null;
    }
}
