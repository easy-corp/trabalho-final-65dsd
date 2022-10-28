package br.udesc.core.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.udesc.util.Config;

public class ClientMock {

    public static void main(String[] args) throws UnknownHostException, IOException {

        Socket client = new Socket("127.0.0.1", Config.SOCKET_PORT);
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
        // BufferedReader input = new BufferedReader(new
        // InputStreamReader(client.getInputStream()));
        Scanner input = new Scanner(System.in);
        String line = "";

        while (!line.equals("QUIT")) {
            line = input.nextLine();
            output.println(line);
        }

        
        input.close();
        output.close();
        client.close();

    }

}
