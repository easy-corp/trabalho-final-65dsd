package com.example.uno.control.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket extends Thread{

    private Socket clientSocket;
    private PrintWriter output;

    private String serverAdress;
    private int port;

    private String message;
    private boolean stop = false;

    public ClientSocket(String serverAdress, int port) throws IOException {
        this.serverAdress = serverAdress;
        this.port = port;
    }

    public void sendMessage(String message){
        this.message = message;
    }

    public void killThread(){
        this.stop = true;
    }


    @Override
    public void run() {
        if(clientSocket == null){
            try {
                clientSocket = new Socket(serverAdress, port);
                output = new PrintWriter(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while(!this.stop){
            if(message != null){
                System.out.println("Tem mensagem!!! " + message);
                output.println(message);
                output.flush();
                message = null;
            }
        }

        try {
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
