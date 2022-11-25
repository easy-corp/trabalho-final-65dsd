package br.udesc.core.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MessageBroker implements ClientSocketListener{

    private MessageBroker(){}

    private static MessageBroker instance;
    private ServerController controller = ServerController.getInstance();
    private Gson gson = new Gson();

    public static MessageBroker getInstance(){
        if(instance == null){
            instance = new MessageBroker();
        }

        return instance;
    }

    @Override
    public void onMessage(String message) {
        JsonObject messageObject = gson.fromJson(message, JsonObject.class);
        System.out.println(messageObject.get("type"));
    }
    

    
}
