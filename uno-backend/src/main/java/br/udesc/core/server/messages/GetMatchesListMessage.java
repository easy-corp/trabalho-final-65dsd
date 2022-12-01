package br.udesc.core.server.messages;

import com.google.gson.JsonObject;

import br.udesc.core.server.ClientSocketThread;

public class GetmatcheslistMessage extends AbstractMessage {

    public GetmatcheslistMessage(ClientSocketThread clientSocket) {
        super(clientSocket);
    }

    @Override
    public void setupProps(JsonObject messageObject) {
        //Nenhuma propriedade para ser setada
    }
 

}