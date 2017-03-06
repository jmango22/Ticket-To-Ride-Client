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
    private String username;
    private String gameName;

    public ClientCommunicator(String host, String port){
        serverHost = host;
        serverPort = port;
    }

    public void setAuthorizationToken(String authorizationToken){
        this.authorizationToken = authorizationToken;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public String getUsername(){
        return username;
    }

    public String getGameName(){
        return gameName;
    }

    public void post(String suffix, JSONObject body, Callback callback){
        String urlText = "http://" + serverHost + ":" + serverPort + suffix;
        new PostTask(body, urlText, this, callback).execute();
    }

    public void get(String suffix, Callback callback){
        String url = "http://" + serverHost + ":" + serverPort + suffix;
        new GetTask(url,this, callback).execute();
    }

    public void setHeader(HttpURLConnection connection){
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

}
