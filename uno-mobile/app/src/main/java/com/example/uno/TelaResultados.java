package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.adapter.AdapterJogadoresResult;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Avatar;
import com.example.uno.model.Match;
import com.example.uno.model.User;
import com.google.gson.Gson;

public class TelaResultados extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    private Match jogo;
    private int jogadorId;

    //RecyclerView
    private RecyclerView listaJogadoresResult;
    private RecyclerView.Adapter adapterJogadores;
    private RecyclerView.LayoutManager layJogadores;

    //Elementos da tela
    private ImageView imgVencedorAvatar;
    private TextView txtVencedorNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultados);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);
        this.imgVencedorAvatar = findViewById(R.id.imgVencedorAvatar);
        this.txtVencedorNome = findViewById(R.id.txtVencedorNome);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(jogadorId))));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class).putExtra("userId", String.valueOf(jogadorId))));
    }

    public void criarRecyclerView() {
        listaJogadoresResult = findViewById(R.id.listaJogadoresResult);

        listaJogadoresResult.setHasFixedSize(true);

        layJogadores = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaJogadoresResult.setLayoutManager(layJogadores);

        adapterJogadores = new AdapterJogadoresResult(this.jogo, this);
        listaJogadoresResult.setAdapter(adapterJogadores);
    }

    public void geraJogadores() throws InterruptedException {
        //Recupera a partida
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
                .withType("get-match")
                .withParam("matchId", getIntent().getStringExtra("matchId"))
                .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        String json = this.message;
        this.jogo = gson.fromJson(json, Match.class);
        this.jogadorId = Integer.parseInt(getIntent().getStringExtra("userId"));

        //Preenche vencedor
        User vencedor = jogo.getPlayers().get(Integer.parseInt(getIntent().getStringExtra("winnerId")));
        vencedor.getAvatar().click(true);

        int image = getResources().getIdentifier(vencedor.getAvatar().getImageUrl(), "drawable", getPackageName());
        this.imgVencedorAvatar.setBackgroundResource(image);

        this.txtVencedorNome.setText(vencedor.getName());
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            geraJogadores();        //Busca partida
            criarRecyclerView();    //Cria lista com os outros jogadores
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