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
import java.net.MalformedURLException;
import java.net.URL;

public class ClientCommunicator {
    private String serverHost;
    private String serverPort;
    private String authorizationToken;
    private JSONObject results;

    public ClientCommunicator(String host, String port){
        serverHost = host;
        serverPort = port;
    }

    public void setAuthorizationToken(){
        try{
            authorizationToken = results.getString("authorization");
            results = null;
        }catch(JSONException e){
            authorizationToken = null;
        }
    }

    public boolean post(String suffix, JSONObject body, String gameName){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + suffix);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            setHeader(connection, gameName);
            connection.setDoOutput(true);
            OutputStream send = connection.getOutputStream();
            output(send, body);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                setResults(connection.getInputStream());
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

    public  boolean get(String suffix, String gameName){
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + suffix);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            setHeader(connection, gameName);
            connection.setDoOutput(false);
            connection.connect();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                setResults(connection.getInputStream());
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

    private void setHeader(HttpURLConnection connection, String gameName){
        if(authorizationToken != null){
            connection.setRequestProperty("authorization", authorizationToken);
        }
        if(gameName != null){
            connection.setRequestProperty("gamename", gameName);
        }
    }

    private void output(OutputStream os, JSONObject data){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(data.toString());
            writer.flush();
            os.close();
        }catch (IOException e){

        }
    }

    private void setResults(InputStream input){
        try {
            StringBuilder string = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line = br.readLine();
            while (line != null) {
                string.append(line);
                line = br.readLine();
            }
            results = new JSONObject(string.toString());

        }catch(JSONException e){
        }catch (IOException e){
        }

    }

}
