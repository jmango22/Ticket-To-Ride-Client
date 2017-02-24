package goldenhammer.ticket_to_ride_client.communication;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

public class ClientCommunicator {
    private String serverHost;
    private String serverPort;
    private String authorizationToken;
    private String results;

    public ClientCommunicator(String host, String port){
        serverHost = host;
        serverPort = port;
    }

    public boolean setAuthorizationToken(){
        try{
            JSONObject resultObject = new JSONObject(results);
            authorizationToken = resultObject.getString("authorization");
            results = null;
        }catch(JSONException e){
            authorizationToken = null;
        }
        if(authorizationToken == null){
            return false;
        }
        return true;
    }

    public String getResults(){
        return results;
    }

    public boolean post(String suffix, JSONObject body, String gameName){
        results = null;
        String urlText = "http://" + serverHost + ":" + serverPort + suffix;
        new PostTask(body, urlText, this, authorizationToken, gameName).execute();
        return true;
    }

    public  boolean get(String suffix, String gameName){
        results = null;
        String url = "http://" + serverHost + ":" + serverPort + suffix;
            new GetTask(url,gameName,this).execute();
            return true;
    }

    public void setHeader(HttpURLConnection connection,  String username, String gameName){
        if(authorizationToken != null){
            connection.setRequestProperty("Authorization", authorizationToken);
        }
        if(gameName != null){
            connection.setRequestProperty("gamename", gameName);
        }
        if (username != null){
            connection.setRequestProperty("Username", username);
        }
    }

    public void output(OutputStream os, JSONObject data){
        if(data!=null) {
            try {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(data.toString());
                writer.flush();
                os.close();
            } catch (IOException e) {

            }
        }
    }

    public void setResults(InputStream input){
        try {
            StringBuilder string = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine();
            while (line != null) {
                string.append(line);
                line = br.readLine();
            }
            results = string.toString();
        }catch (IOException e){
        }

    }

}
