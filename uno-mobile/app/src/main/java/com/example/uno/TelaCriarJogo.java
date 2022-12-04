package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Match;
import com.example.uno.model.User;
import com.google.gson.Gson;

public class TelaCriarJogo extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    private int jogadorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_criar_jogo);

        this.gson = new Gson();
        this.service = this;

        this.jogadorId = Integer.parseInt(this.getIntent().getStringExtra("userId"));

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);
        EditText edTituloPartida = findViewById(R.id.edTituloPartida);
        EditText edMaxJogadores = findViewById(R.id.edMaxJogadores);
        Button btnCriarPartida = findViewById(R.id.btnCriarPartida);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(jogadorId))));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class).putExtra("userId", String.valueOf(jogadorId))));

        btnCriarPartida.setOnClickListener(param -> {
            try {
                criarPartida(edTituloPartida.getText().toString(), edMaxJogadores.getText().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void criarPartida(String nome, String qtdPlayers) throws InterruptedException {
        if (nome.isEmpty() || qtdPlayers.isEmpty()) {
            exibirMensagem("Você precisa preencher todos os campos para realizar o cadastro.");
        } else {
            if (Integer.valueOf(qtdPlayers) < 2) {
                exibirMensagem("São necessários pelo menos 2 jogadores para criar uma partida.");
            } else {
                //Cria a mensagem e envia ao servidor
                String msg = new MessageBuilder()
                    .withType("create-match")
                    .withParam("name", nome)
                    .withParam("qtdPlayers", qtdPlayers)
                    .build();

                binder.getService().enviarMensagem(msg);

                Thread.sleep(500);

                //Valor retornado pelo server
                String json = this.message;

                //Transforma o Gson novamente em um tipo User
                Match match = gson.fromJson(json, Match.class);

                if (match == null) {
                    exibirMensagem("Ocorreu algo errado com seu cadastro, tente novamente mais tarde.");
                } else {
                    startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(jogadorId)));
                }
            }
        }
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