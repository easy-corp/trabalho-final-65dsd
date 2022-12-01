package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class GetMatchesListMessage extends AbstractMessage {

    public GetMatchesListMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        //Nenhuma propriedade para ser setada
    }
 

}