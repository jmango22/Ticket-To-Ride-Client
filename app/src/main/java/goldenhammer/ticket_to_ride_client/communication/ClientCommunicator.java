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
            if(authorizationToken == null){
                return false;
            }
            return true;
        }catch(JSONException e){
            authorizationToken = null;
            return false;
        }
    }

    public String getResults(){
        return results;
    }

    public boolean post(String suffix, JSONObject body, String gameName){
        results = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + suffix);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            setHeader(connection, gameName);
            connection.setDoOutput(true);
            setHeader(connection, gameName);
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
        results = null;
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + suffix);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            setHeader(connection, gameName);
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
            results = string.toString();
        }catch (IOException e){
        }

    }

}
