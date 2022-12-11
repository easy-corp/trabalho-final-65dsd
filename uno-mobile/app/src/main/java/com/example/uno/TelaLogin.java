package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.User;
import com.google.gson.Gson;

public class TelaLogin extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        EditText edUsuario = findViewById(R.id.edUsuario);
        EditText edSenha = findViewById(R.id.edSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        btnEntrar.setOnClickListener(param -> {
            try {
                login(edUsuario.getText().toString(), edSenha.getText().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        btnCadastrar.setOnClickListener(param -> startActivity(new Intent(this, TelaCadastro.class)));
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    private void login(String usuario, String senha) throws InterruptedException {
        if (usuario.isEmpty() || senha.isEmpty()) {
            exibirMensagem("Você precisa inserir usuário e senha para poder realizar o login.");
        } else {
            //Cria a mensagem de login e a envia ao servidor
            String msg = new MessageBuilder()
                .withType("login")
                .withParam("username", usuario)
                .withParam("password", senha)
                .build();

            binder.getService().enviarMensagem(msg);

            Thread.sleep(500);

            //Valor retornado pelo server
            String json = this.message;

            //Transforma o Gson novamente em um tipo User
            User user = gson.fromJson(json, User.class);

            //Se o login funcionar, o servidor devolve um objeto User
            if (user == null) {
                exibirMensagem("Usuário ou senha inválidos.");
            } else {
                startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(user.getUserId())));
            }
        }
    }

    private void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        //Envia uma mensagem de teste ao servidor
        //Aguarda uma resposta para verificar se o servidor está ok
        String msg = new MessageBuilder()
            .withType("test-connection")
            .build();

        this.binder.getService().enviarMensagem(msg);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.message == null) {
            startActivity(new Intent(this, TelaInicial.class).putExtra("offServer", "true"));
        }
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