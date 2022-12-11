package com.example.uno.control.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocket extends Thread {

    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;

    private String serverAdress;
    private int port;

    private String message;
    private boolean stop = false;
    private List<IMessageListener> listeners;

    public ClientSocket(String serverAddress, int port) throws IOException {
        this.serverAdress = serverAddress;
        this.port = port;
        this.listeners = new ArrayList<>();
    }

    public void addListener(IMessageListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IMessageListener listener) {
        this.listeners.remove(listener);
    }

    public void sendMessage(String message) {
        this.message = message;
    }

    public void killThread() {
        this.stop = true;
    }


    @Override
    public void run() {
        if (clientSocket == null) {
            try {
                clientSocket = new Socket(serverAdress, port);
                output = new PrintWriter(clientSocket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                this.killThread();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stop) {
                    if (message != null) {
                        output.println(message);
                        output.flush();
                        message = null;
                    }
                }
            }
        }).start();

        while (!this.stop) {
            try {
                String incomingMessage = input.readLine();
                if (incomingMessage != null) {
                    //Avisa todos que estiverem escutando
                    for (IMessageListener listen : this.listeners) {
                        listen.onMessage(incomingMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            if (output != null && clientSocket != null) {
                output.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
