package br.udesc.core.server.messages;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import br.udesc.core.model.Card;
import br.udesc.core.server.ClientSocketThread;

public class BuyCardAutoMessage extends AbstractMessage {

    private List<Card> cardsBuyed;
    private int userId;
    private int matchId;
    private Gson gson;

    public BuyCardAutoMessage(ClientSocketThread clientSocket) {
        super(clientSocket);

        this.gson = new Gson();
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.userId = messageObject.get("userId").getAsInt();
        this.matchId = messageObject.get("matchId").getAsInt();

        Type listType = new TypeToken<List<Card>>(){}.getType();
        List<Card> cards = gson.fromJson(messageObject.get("cards").getAsString(), listType);
        this.cardsBuyed = cards;
    }

    public List<Card> getCardsBuyed() {
        return cardsBuyed;
    }

    public void setCardsBuyed(List<Card> cardsBuyed) {
        this.cardsBuyed = cardsBuyed;
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
