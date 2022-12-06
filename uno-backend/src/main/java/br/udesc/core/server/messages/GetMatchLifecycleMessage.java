package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

import br.udesc.core.server.ClientSocketThread;

public class GetMatchLifecycleMessage extends AbstractMessage {

    private int matchId;

    public GetMatchLifecycleMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.matchId = messageObject.get("matchId").getAsInt();
    }

    public void setMatchId(int id) {
        this.matchId = id;
    }

    public int getMatchId() {
        return this.matchId;
    }

}