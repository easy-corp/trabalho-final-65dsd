package br.udesc.core.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;

import br.udesc.core.model.User;
import br.udesc.util.Config;

public class Server {

    private Logger logger;
    private List<ClientSocketThread> clientSockets;
    private Map<Integer, ClientSocketThread> clients;     // Lista de clients conectados
    private static Server instance;
    private Gson gson = new Gson();

    private Server() {
        this.logger = Logger.getLogger("ServerLogger");
        this.clientSockets = new ArrayList<>();
        this.clients = new HashMap();
    }

    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }

        return instance;
    }

    public void bindUser(User user, ClientSocketThread socketClient){
        this.clients.put(user.getId(), socketClient);
        socketClient.setUser(user);
    }

    public ClientSocketThread getClientSocket(int userId){
        return this.clients.get(userId);
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
                    clientSockets.add(clientThread);

                    //Adiciona um listener para as mensagens vindas do cliente
                    clientThread.addListener(MessageBroker.getInstance());

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

    public Map<Integer, ClientSocketThread> getClients() {
        return this.clients;
    }

}
