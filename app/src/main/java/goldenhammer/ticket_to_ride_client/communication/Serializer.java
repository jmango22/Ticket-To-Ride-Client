package goldenhammer.ticket_to_ride_client.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import goldenhammer.ticket_to_ride_client.model.GameList;
import goldenhammer.ticket_to_ride_client.model.GameModel;
import goldenhammer.ticket_to_ride_client.model.commands.Command;

/**
 * Created by devonkinghorn on 3/4/17.
 */

public class Serializer {
    public static String serialize(Object c) {
        Gson gson = new Gson();
        return gson.toJson(c);
    }
    public static JsonObject deserialize(String json) {
        JsonParser parser = new JsonParser();
        return parser.parse(json).getAsJsonObject();
    }
    static final String commandPackagePrefix = "edu.goldenhammer.ticket_to_ride_client.model.commands";
    public static Command deserializeCommand(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String commandName = jsonObject.get("name").getAsString();
        StringBuilder sb = new StringBuilder(commandName);
        commandName = sb.replace(0, 1, sb.substring(0, 1).toUpperCase()).toString();
        String className = commandPackagePrefix + commandName + "Command";
        Command basecmd = null;
        try {
            Class c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            basecmd = (Command)gson.fromJson(json, c);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return basecmd;
    }


    public static List<Command> deserializeCommands(String commandString) {
        Gson gson = new Gson();
        JsonArray commands = gson.fromJson(commandString, JsonArray.class);
        List<Command> list = new ArrayList<>();
        for(int i = 0; i < commands.size(); i++) {
            try {
                Command c = deserializeCommand(commands.get(i).getAsString());
                list.add(c);
            } catch (JsonSyntaxException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public static GameModel deserializeGameModel(String result){
        Gson gson = new Gson();
        return gson.fromJson(result, GameModel.class);
    }

    public static GameList deserializeGames(String result){
        Gson gson = new Gson();
        return gson.fromJson(result,GameList.class);
    }

    public static String deserializeMessage(String result){
        try{
            JSONObject message = new JSONObject(result);
            return message.getString("message");
        }catch (JSONException e){
            return result;
        }
    }
}
