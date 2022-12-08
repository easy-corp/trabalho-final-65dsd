package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class GetMatchMessage extends AbstractMessage {

    private int matchId;

    public GetMatchMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.matchId = messageObject.get("matchId").getAsInt();
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

}
