package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uno.control.NotificationService;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.ServiceSocket;
import com.google.firebase.messaging.FirebaseMessaging;

public class TelaInicial extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        EditText edIP = findViewById(R.id.edUsuario);
        EditText edPorta = findViewById(R.id.edSenha);
        Button btnConectar = findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(param -> {
            try {
                conectar(edIP.getText().toString(), edPorta.getText().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                System.out.println("retrieve token successful : " + token);
            } else{
                System.out.println("token should not be null...");
            }
        }).addOnFailureListener(e -> {
            System.out.println(e);
        }).addOnCanceledListener(() -> {
            //handle cancel
        }).addOnCompleteListener(task ->
                System.out.println("This is the token : " + task.getResult()));
    }

    private void conectar(String ip, String porta) throws InterruptedException {
        if (ip.isEmpty() || porta.isEmpty()) {
            exibirMensagem("Você precisa inserir ip e porta para poder se conectar.");
        } else {

            if (ip.contains(".")) {
                //Binda o serviço nessa Activity
                bindService(new Intent(this, ServiceSocket.class), service, Context.BIND_AUTO_CREATE);
                startService(ip, porta);

                if (testarConexao()) {
                    startActivity(new Intent(this, TelaLogin.class));
                } else {
                    exibirMensagem(ip + ":" + porta + " não é um servidor ativo.");
                }
            } else {
                exibirMensagem("IP inválido.");
            }
        }
    }

    private boolean testarConexao() throws InterruptedException {
        //Aguarda um tempo e verifica se o Binder estará disponível
        Thread.sleep(500);

        if (this.binder == null) {
            return false;
        } else {
            return true;
        }
    }

    private void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startService(String ip, String porta) {
        //Cria a intent
        Intent intent = new Intent(this, ServiceSocket.class);

        //Adiciona os parâmetros lá
        intent.putExtra("ip", ip);
        intent.putExtra("porta", porta);

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, Context.BIND_AUTO_CREATE);

        startService(intent);
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);
    }

    //Quando o serviço for desbindado
    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        this.service = null;
    }

    //Quando a tela pausar
    @Override
    public void onPause() {
        super.onPause();
        if (binder != null) {
            binder.removeListener(this);
        }
    }

    //Quando a tela voltar
    @Override
    public void onResume() {
        super.onResume();
        if (binder != null) {
            binder.addListener(this);
        }
    }

    //Esperando mensagens
    @Override
    public void onMessage(String message) {
        this.message = message;
    }

}