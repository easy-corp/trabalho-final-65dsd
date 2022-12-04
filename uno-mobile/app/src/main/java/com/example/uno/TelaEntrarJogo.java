package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.adapter.AdapterJogos;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Match;
import com.example.uno.model.GameServer;
import com.example.uno.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class TelaEntrarJogo extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    private int jogadorId;

    private RecyclerView listaJogos;
    private RecyclerView.Adapter adapterJogos;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrar_jogo);

        this.gson = new Gson();
        this.service = this;

        this.jogadorId = Integer.parseInt(this.getIntent().getStringExtra("userId"));

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);
        ImageView icAddMatch = findViewById(R.id.icAddMatch);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaLogin.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class).putExtra("userId", String.valueOf(jogadorId))));

        icAddMatch.setOnClickListener(param -> startActivity(new Intent(this, TelaCriarJogo.class).putExtra("userId", String.valueOf(jogadorId))));
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    public void criarRecyclerView() throws InterruptedException {
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
            .withType("get-matches-list")
            .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em uma lista de Mtaches
        Type listType = new TypeToken<Map<Integer, Match>>(){}.getType();
        Map<Integer, Match> matches = gson.fromJson(json, listType);

        listaJogos = findViewById(R.id.listaJogos);

        listaJogos.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaJogos.setLayoutManager(layoutManager);

        adapterJogos = new AdapterJogos(this, matches, jogadorId);
        listaJogos.setAdapter(adapterJogos);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_vertical));
        listaJogos.addItemDecoration(divisor);
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            criarRecyclerView();
        } catch (InterruptedException e) {
            e.printStackTrace();
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