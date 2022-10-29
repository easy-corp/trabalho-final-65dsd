package br.udesc.core.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocketThread extends Thread {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Logger logger;
    private ClientSocketListener listener;

    public ClientSocketThread(Socket socketClient) {
        socket = socketClient;
        logger = Logger.getLogger("ClientThread");
        initIO();
    }

    public void setListener(ClientSocketListener listener) {
        this.listener = listener;
    }

    private void initIO() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao iniciar a comunicacao com o cliente!", e);
        }
    }

    public void sendMessage(String message){
        writer.println(message);
        writer.flush();
    }

    @Override
    public void run() {
        String readLine;

        try {
            readLine = reader.readLine();
            while (readLine == null || !readLine.equals("QUIT")) {
                if (readLine != null) {
                    listener.onMessage(readLine);
                }
                readLine = reader.readLine();
                
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao receber os dados do cliente!", e);
        } finally {
            logger.log(Level.INFO, "Fechando conexao com o cliente " + getName());
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erro ao encerrar a conexao com o cliente!", e);
            }

        }
    }

}
