package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.model.Card;
import br.udesc.core.model.Card.Color;
import br.udesc.core.server.ClientSocketThread;

public class BuyCardMessage extends AbstractMessage {

    private Card cardBuyed;
    private int userId;
    private int matchId;

    public BuyCardMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        this.userId = messageObject.get("userId").getAsInt();
        this.matchId = messageObject.get("matchId").getAsInt();

        String cardSymbol = messageObject.get("cardSymbol").getAsString();
        Color c = Color.valueOf(messageObject.get("cardColor").getAsString());
        String url = messageObject.get("cardImageUrl").getAsString();
        this.cardBuyed = new Card(cardSymbol, c, url);
    }

    public Card getCardBuyed() {
        return cardBuyed;
    }

    public void setCardBuyed(Card cardBuyed) {
        this.cardBuyed = cardBuyed;
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
