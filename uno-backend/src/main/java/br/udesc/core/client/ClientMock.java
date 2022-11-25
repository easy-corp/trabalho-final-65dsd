package br.udesc.core.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.udesc.util.Config;

public class ClientMock {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket client = new Socket("127.0.0.1", Config.SOCKET_PORT);
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
        BufferedReader inputSocket = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Scanner input = new Scanner(System.in);
        String line = "";


        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean connected = true;
                while(connected){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        String s = inputSocket.readLine();
                        if(s != null){
                            System.out.println(s);
                        }
                    } catch (IOException e) {
                        Logger.getGlobal().log(Level.INFO, "desconectando...");
                        connected = false;
                    }
                }
            }
            
        });

        t.start();

        while (!line.equals("QUIT") && client.isConnected()) {
            line = input.nextLine();
            output.println(line);
        }     
        
        client.close();
        input.close();
        output.close();

    }

}
