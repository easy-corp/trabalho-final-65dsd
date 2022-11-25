package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class JoinMatchMessage extends AbstractMessage {

    private int userId;
    private int matchId;

    public JoinMatchMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.userId = messageObject.get("userId").getAsInt();
        this.matchId = messageObject.get("matchId").getAsInt();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

}
