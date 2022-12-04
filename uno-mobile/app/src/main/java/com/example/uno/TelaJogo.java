package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uno.control.adapter.AdapterCartasJogador;
import com.example.uno.control.adapter.AdapterJogadores;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Avatar;
import com.example.uno.model.Card;
import com.example.uno.model.User;
import com.example.uno.model.Match;
import com.google.gson.Gson;

import java.util.Random;

public class TelaJogo extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    private Match jogo;
    private User jogador;
    private Random random = new Random();
    private Card cartaVirada = null;

    //RecyclerViews
    private RecyclerView listaJogadores;
    private RecyclerView listaCartasJogador;
    private RecyclerView.Adapter adapterJogadores;
    private RecyclerView.Adapter adapterCartasJogador;
    private RecyclerView.LayoutManager layManagerJogadores;
    private RecyclerView.LayoutManager layManagerCartasJogador;

    private Boolean isFront = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icSairJogo = findViewById(R.id.icSairJogo);
        FrameLayout layUno = findViewById(R.id.layUno);

//        icSairJogo.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));
        icSairJogo.setOnClickListener(param -> startActivity(new Intent(this, TelaResultados.class).putExtra("userId", String.valueOf(jogador.getId()))));
        layUno.setOnClickListener(param -> pedirUno());
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    public Match getJogo() {
        return this.jogo;
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Gera lista de jogadores na sala
    private void criarRecyclerViewJogadores() throws InterruptedException {
        listaJogadores = findViewById(R.id.listaJogadoresJogo);

        listaJogadores.setHasFixedSize(true);

        layManagerJogadores = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaJogadores.setLayoutManager(layManagerJogadores);

        adapterJogadores = new AdapterJogadores(this.jogo, this);
        listaJogadores.setAdapter(adapterJogadores);
    }

    //Gera lista de cartas na mão do jogador
    private void criarRecyclerViewCartas() {
        listaCartasJogador = findViewById(R.id.listaCartasJogador);

        listaCartasJogador.setHasFixedSize(true);

        layManagerCartasJogador = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaCartasJogador.setLayoutManager(layManagerCartasJogador);

        adapterCartasJogador = new AdapterCartasJogador(this, this.jogador);
        listaCartasJogador.setAdapter(adapterCartasJogador);
    }

    //Gera carta inicial virada para cima na mesa
    private void gerarCartaMesa() {
        //Pega uma carta aleatoriamente para botar na mesa
        Card carta = this.jogo.getDeck().get(random.nextInt(this.jogo.getDeck().size()));

        //Ela não pode ser preta
        if (carta.getColor() == Card.Color.BLACK) {
            gerarCartaMesa();
        } else {
            //Tira ela do baralho e bota na pilha de descartadas
            jogo.getDeck().remove(carta);
            jogo.getDiscard().add(carta);

            //Atualiza a imagem na tela
            atualizarCartaMesa(carta);
        }
    }

    //Atualizar a carta da mesa ao descartar carta
    public void atualizarCartaMesa(Card carta) {
        ImageView imgCartaViradaMesa = findViewById(R.id.imgCartaViradaMesa);
        ImageView imgProximaCartaViradaMesa = findViewById(R.id.imgProximaCartaViradaMesa);

        if (imgProximaCartaViradaMesa.getBackground() != null) {
            imgCartaViradaMesa.setBackground(imgProximaCartaViradaMesa.getBackground());
        }

        //Adiciona a pilha de descartadas
        jogo.getDiscard().add(carta);

        //Move a carta de cima para fora da tela
        ObjectAnimator moveOut = ObjectAnimator.ofFloat(imgProximaCartaViradaMesa, "translationX", 1000f);
        moveOut.setDuration(0);
        moveOut.start();

        //Atualiza a imagem de cima na tela
        int image = getResources().getIdentifier(jogo.getDiscard().peek().getImageUrl(), "drawable", getPackageName());
        imgProximaCartaViradaMesa.setBackgroundResource(image);

        //Move a carta de cima para dentro da tela
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(imgProximaCartaViradaMesa, "translationX", 0);
        moveIn.setDuration(600);
        moveIn.start();
    }

    //Cria animações na tela para compra de cartas na mesa
    private void comprarCarta() {
        ImageView imgMonte = findViewById(R.id.imgMonte);
        ImageView imgMonteVirado = findViewById(R.id.imgMonteVirado);
        AnimatorSet flipFront = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_front);
        AnimatorSet flipBack = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_back);
        Float scale = getApplicationContext().getResources().getDisplayMetrics().density;

        imgMonte.setCameraDistance(8000 * scale);
        imgMonteVirado.setCameraDistance(8000 * scale);

        imgMonte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFront) {
                    //Recupera e troca a carta para o flip seguinte
                    cartaVirada = jogo.getDeck().get(random.nextInt(jogo.getDeck().size()));
                    int image = getResources().getIdentifier(cartaVirada.getImageUrl(), "drawable", getPackageName());
                    imgMonteVirado.setBackgroundResource(image);

                    //Move a carta de volta para baixo do monte
                    ObjectAnimator moveIn = ObjectAnimator.ofFloat(imgMonteVirado, "translationY", 0);
                    moveIn.setDuration(0);
                    moveIn.start();

                    //Flipa a carta de costas
                    flipFront.setTarget(imgMonte);
                    flipBack.setTarget(imgMonteVirado);

                    flipFront.start();
                    flipBack.start();

                    isFront = false;
                } else {
                    //Tira a carta da tela
                    ObjectAnimator moveOut = ObjectAnimator.ofFloat(imgMonteVirado, "translationY", 1000f);
                    moveOut.setDuration(600);
                    moveOut.start();

                    //Volta a de costas para cima
                    flipFront.setTarget(imgMonteVirado);
                    flipBack.setTarget(imgMonte);

                    flipBack.start();
                    flipFront.start();

                    isFront = true;

                    //Adiciona a carta na mão do jogador
                    jogador.getDeck().add(cartaVirada);
                    jogo.getDeck().remove(cartaVirada);

                    //Atualiza as listas
                    atualizarListas();
                }
            }
        });
    }

    //Distribui as cartas entre os jogadores
    private void distribuiCartas() throws InterruptedException {
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
            .withType("my-profile")
            .withParam("userId", getIntent().getStringExtra("userId"))
            .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        this.jogador = gson.fromJson(json, User.class);

        //Adiciona o jogador logado no dispositivo
        //Cria a mensagem e envia ao servidor
        msg = new MessageBuilder()
            .withType("join-match")
            .withParam("userId", String.valueOf(jogador.getId()))
            .withParam("matchId", getIntent().getStringExtra("matchId"))
            .build();

        binder.getService().enviarMensagem(msg);
        Thread.sleep(500);

        //Faz uma nova consulta pela partida
        atualizarPartida();
    }

    public void atualizarPartida() throws InterruptedException {
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
                .withType("get-match-lifecycle")
                .withParam("matchId", getIntent().getStringExtra("matchId"))
                .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        System.out.println(json);

        //Transforma o Gson novamente em um tipo Match
        Match match = gson.fromJson(json, Match.class);
        this.jogo = match;

        //Atualiza o jogador também com base nos novos dados
        this.jogador = this.jogo.getPlayers().get(jogador.getId());

        atualizarListas();
    }

    //Atualizar as listas na tela quando os dados mudarem
    public void atualizarListas() {
        if (adapterJogadores != null) {
            adapterJogadores.notifyDataSetChanged();
        }

        if (adapterCartasJogador != null) {
            adapterCartasJogador.notifyDataSetChanged();
        }
    }

    public void pedirUno() {
        ImageView icPedirUno = findViewById(R.id.icPedirUno);
        TextView txtPedirUno = findViewById(R.id.txtPedirUno);

        icPedirUno.setBackgroundResource(R.drawable.mao_uno_selecionado);
        txtPedirUno.setTextColor(Color.parseColor("#ED1C24"));

        this.jogador.setIsUno(true);
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            distribuiCartas();
            criarRecyclerViewJogadores();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        criarRecyclerViewCartas();
        comprarCarta();
        gerarCartaMesa();
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