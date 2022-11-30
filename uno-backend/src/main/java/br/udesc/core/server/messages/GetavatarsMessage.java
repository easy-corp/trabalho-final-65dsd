package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class GetavatarsMessage extends AbstractMessage {

    public GetavatarsMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        //Nenhuma propriedade para ser setada
    }
    
}
