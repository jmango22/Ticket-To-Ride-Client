package goldenhammer.ticket_to_ride_client.communication;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import goldenhammer.ticket_to_ride_client.model.ChatMessages;
import goldenhammer.ticket_to_ride_client.model.Message;
import goldenhammer.ticket_to_ride_client.model.commands.BaseCommand;

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
    static final String commandPackagePrefix = "goldenhammer.ticket_to_ride_client.model.commands.";
    public static BaseCommand deserializeCommand(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        String commandName = jsonObject.get("name").getAsString();
        StringBuilder sb = new StringBuilder(commandName);
        commandName = sb.replace(0, 1, sb.substring(0, 1).toUpperCase()).toString();
        String className = commandPackagePrefix + commandName + "Command";
        BaseCommand basecmd = null;
        try {
            Class c = null;
            try {
                c = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            basecmd = (BaseCommand)gson.fromJson(json, c);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return basecmd;
    }


    public static List<BaseCommand> deserializeCommands(String commandString) {
        Gson gson = new Gson();
        JsonArray commands = gson.fromJson(commandString, JsonArray.class);
        List<BaseCommand> list = new ArrayList<>();
        for(int i = 0; i < commands.size(); i++) {
            try {
                BaseCommand c = deserializeCommand(commands.get(i).toString());
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

    public static ChatMessages deserializeChat(String result){
            Gson gson = new Gson();
            List<Message> messages = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject e = array.getJSONObject(i);
                    String username = e.getString("username");
                    String message = e.getString("message");
                    Message m = new Message(username,message);
                    //Message m = gson.fromJson(e.toString(),Message.class);
                    messages.add(m);
                }
            }catch (JSONException e){

            }
            return new ChatMessages(messages);
    }
}
