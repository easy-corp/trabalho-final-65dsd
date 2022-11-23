package br.udesc.core.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.udesc.util.Config;

public class Server {

    private Logger logger;

    public Server() {
        this.logger = Logger.getLogger("ServerLogger");
    }

    public void startServer() {

        Socket socket;
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(Config.SOCKET_PORT);
            logger.log(Level.INFO, "Servidor aberto na porta " + Config.SOCKET_PORT);

            try {
                while (true) {

                    socket = serverSocket.accept();
                    logger.log(Level.INFO, "Conexão com o cliente " + socket.getLocalAddress() + " efetuada!");

                    //Cria uma Thread para o cliente
                    ClientSocketThread clientThread = new ClientSocketThread(socket);

                    //Adiciona um listener para as mensagens vindas do cliente
                    clientThread.setListener(new ClientSocketListener() {

                        @Override
                        public void onMessage(String message) {
                            logger.info("O cliente enviou uma mensagem: " + message);
                        }

                    });

                    //Inicia a thread do cliente
                    clientThread.start();

                }

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Erro ao iniciar conexão com o cliente", e);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao iniciar o servidor na portal " + Config.SOCKET_PORT, e);
        }

    }

}
