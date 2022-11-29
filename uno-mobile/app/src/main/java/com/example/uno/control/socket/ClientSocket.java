package com.example.uno.control.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket extends Thread {

    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;

    private String serverAdress;
    private int port;

    private String message;
    private boolean stop = false;
    private IMessageListener listener;

    public ClientSocket(String serverAddress, int port, IMessageListener listener) throws IOException {
        this.serverAdress = serverAddress;
        this.port = port;
        this.listener = listener;
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
                    listener.onMessage(incomingMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
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
