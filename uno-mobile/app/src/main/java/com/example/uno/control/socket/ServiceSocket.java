package com.example.uno.control.socket;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSocket extends Service {

    private Gson gson;
    private ClientSocket socket;
    private String message;
    private final IBinder bind = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Serviço iniciado");

        //Inicia o GSON
        this.gson = new Gson();

        //Parâmetros de IP e PORTA vem na intent criada ao setar o service
        String address = intent.getStringExtra("ip") + ":" + intent.getStringExtra("porta");

        try {
            createSocketClient(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Conectado com: " + address);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //Termina a thread;
        this.socket.killThread();

        super.onDestroy();
    }

    //Cria o socket
    private void createSocketClient(String serverAddress) throws IOException {
        //Recupera o ip e porta vindo como string
        String[] parts = serverAddress.split(":");
        String ip = parts[0];
        int port = Integer.parseInt(parts[1]);

        //Cria um novo socket e deixa um listener esperando a mensagem vir
        socket = new ClientSocket(ip, port);

        IMessageListener listener = new IMessageListener() {
            @Override
            public void onMessage(String msg) {
                message = msg;
            }
        };

        socket.addListener(listener);

        //Inicia o socket
        socket.start();
    }

    public void enviarMensagem(String msg) {
        this.socket.sendMessage(msg);
    }

    public class LocalBinder extends Binder {

        public ServiceSocket getService() {
            return ServiceSocket.this;
        }

        public void addListener(IMessageListener listener) {
            ServiceSocket.this.socket.addListener(listener);
        }

        public void removeListener(IMessageListener listener) {
            ServiceSocket.this.socket.removeListener(listener);
        }

        public String teste() {
            return "Estou aqui";
        }

    }

}