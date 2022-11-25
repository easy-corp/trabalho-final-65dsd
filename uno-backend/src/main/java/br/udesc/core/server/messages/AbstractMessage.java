package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public abstract class AbstractMessage {

    private ClientSocketThread socketClient;

    public AbstractMessage(ClientSocketThread clientSocket){
        this.socketClient = clientSocket;
    }

    public abstract void setupProps(JsonObject messageObject);

    public void sendReply(String messageReply){
        socketClient.sendMessage(messageReply);
    }

    public ClientSocketThread getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(ClientSocketThread socketClient) {
        this.socketClient = socketClient;
    }

}
