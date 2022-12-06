package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

public class MessageBuilder {

    private JsonObject messageObject;

    public MessageBuilder(){
        this.messageObject = new JsonObject();
    }

    public MessageBuilder withType(String type){
        this.messageObject.addProperty("type", type);
        return this;
    }

    public MessageBuilder withParam(String paramName, String value){
        this.messageObject.addProperty(paramName, value);
        return this;
    }

    public String build(){
        return this.messageObject.toString();
    }

}
