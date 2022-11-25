package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class LoginMessage extends AbstractMessage {

    private String username;
    private String password;

    public LoginMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.username = messageObject.get("username").getAsString();
        this.password = messageObject.get("password").getAsString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
