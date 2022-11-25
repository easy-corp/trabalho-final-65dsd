package br.udesc.core.server;

public interface ClientSocketListener {

    public abstract void onMessage(String message, ClientSocketThread clientSocket);
    
}
