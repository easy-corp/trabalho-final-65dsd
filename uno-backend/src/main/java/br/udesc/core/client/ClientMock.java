package br.udesc.core.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
                while(true){
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
                        e.printStackTrace();
                    }
                }
            }
            
        });

        t.start();

        while (!line.equals("QUIT")) {
            line = input.nextLine();
            output.println(line);
        }


        
        input.close();
        output.close();
        client.close();

    }

}
