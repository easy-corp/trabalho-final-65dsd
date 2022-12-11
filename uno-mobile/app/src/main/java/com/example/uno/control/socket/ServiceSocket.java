package com.example.uno.control.socket;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.uno.R;
import com.example.uno.TelaJogo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSocket extends Service {

    private Gson gson;
    private ClientSocket socket;
    private String message;
    private final IBinder bind = new LocalBinder();
    private Context context;
    private NotificationManager manager;
    private boolean isPause = false;

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


        // Notificações
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal Principal";
            String description = "Meu canal de notificações";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("mainChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

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

                if (isPause) {
                    if (msg.contains("match-started")) {
                        enviarNotificacao("A partida começou.");
                    } else if (msg.contains("notify-turn")) {
                        enviarNotificacao("É sua vez, venha jogar!");
                    }
                }
            }
        };

        socket.addListener(listener);

        //Inicia o socket
        socket.start();
    }

    public void enviarMensagem(String msg) {
        this.socket.sendMessage(msg);
    }

    public void enviarNotificacao(String msg) {
        // Create an instance of the NotificationCompat.Builder class
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_uno)
                .setContentTitle("Uno")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId("mainChannel");

        Intent intent = new Intent(context, TelaJogo.class);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_MUTABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setContentIntent(pi);

        // Use the NotificationManager to show the notification
        manager.notify(1, builder.build());
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

        public void addContext(Context context) {
            ServiceSocket.this.context = context;
        }

        public void addManager(NotificationManager manager) {
            ServiceSocket.this.manager = manager;
        }

        public void setIsPause(boolean opt) {
            ServiceSocket.this.isPause = opt;
        }

    }

}