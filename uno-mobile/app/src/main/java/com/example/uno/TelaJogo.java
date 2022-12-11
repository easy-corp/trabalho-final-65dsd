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
import com.example.uno.model.Card;
import com.example.uno.model.User;
import com.example.uno.model.Match;
import com.example.uno.model.message.TypedMessage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
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
    private boolean myTurn = false;
    private int userJogada;
    private boolean isFront = true;

    //RecyclerViews
    private RecyclerView listaJogadores;
    private RecyclerView listaCartasJogador;
    private RecyclerView.Adapter adapterJogadores;
    private RecyclerView.Adapter adapterCartasJogador;
    private RecyclerView.LayoutManager layManagerJogadores;
    private RecyclerView.LayoutManager layManagerCartasJogador;

    ImageView icSairJogo;
    FrameLayout layUno;
    FrameLayout layReady;
    ImageView icPedirUno;
    TextView txtPedirUno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        icSairJogo = findViewById(R.id.icSairJogo);
        layUno = findViewById(R.id.layUno);
        layReady = findViewById(R.id.layReady);
        icPedirUno = findViewById(R.id.icPedirUno);
        txtPedirUno = findViewById(R.id.txtPedirUno);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        icSairJogo.setOnClickListener(param -> {
            try {
                quitMatch();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        layReady.setOnClickListener(param -> {
            try {
                prontoParaJogar();
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

    public void habilitarPedirUno(boolean opt) {
        if (opt) {
            layUno.setOnClickListener(param -> pedirUno());
        } else {
            icPedirUno.setBackgroundResource(R.drawable.mao_uno_nao_selecionado);
            txtPedirUno.setTextColor(Color.parseColor("#595959"));
            layUno.setOnClickListener(param -> exibirMensagem("Você não pode pedir Uno"));
        }

    }

    public Match getJogo() {
        return this.jogo;
    }

    public boolean getMyTurn() {
        return this.myTurn;
    }

    public void setMyTurn(boolean opt) {
        this.myTurn = opt;
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Sai da partida
    public void quitMatch() throws InterruptedException {
        String msg = new MessageBuilder()
            .withType("quit-match")
            .withParam("userId", getIntent().getStringExtra("userId"))
            .withParam("matchId", getIntent().getStringExtra("matchId"))
            .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(jogador.getUserId())));
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

        adapterCartasJogador = new AdapterCartasJogador(this.jogador, this);
        listaCartasJogador.setAdapter(adapterCartasJogador);
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

    public void enviarCartaJogada(Card carta) {
        String msg = new MessageBuilder()
            .withType("play-card")
            .withParam("userId", String.valueOf(jogador.getUserId()))
            .withParam("matchId", String.valueOf(this.getJogo().getMatchId()))
            .withParam("cardSymbol", carta.getSimbolo())
            .withParam("cardColor", String.valueOf(carta.getColor()))
            .withParam("cardImageUrl", carta.getImageUrl())
            .build();

        binder.getService().enviarMensagem(msg);
    }

    public void enviarCartaComprada(Card carta) {
        String msg = new MessageBuilder()
                .withType("buy-card")
                .withParam("userId", String.valueOf(jogador.getUserId()))
                .withParam("matchId", String.valueOf(this.getJogo().getMatchId()))
                .withParam("cardSymbol", carta.getSimbolo())
                .withParam("cardColor", String.valueOf(carta.getColor()))
                .withParam("cardImageUrl", carta.getImageUrl())
                .build();

        binder.getService().enviarMensagem(msg);
    }

    public void enviarCartaAutomatica(List<Card> cartas) {
        String msg = new MessageBuilder()
                .withType("buy-card-auto")
                .withParam("userId", String.valueOf(jogador.getUserId()))
                .withParam("matchId", String.valueOf(this.getJogo().getMatchId()))
                .withParam("cards", gson.toJson(cartas))
                .build();

        binder.getService().enviarMensagem(msg);
    }

    public void compraAutomatica(int qtd) throws InterruptedException {
        exibirMensagem("Você precisa comprar " + qtd + " cartas");
        List<Card> cartas = new ArrayList<>();

        ImageView imgMonte = findViewById(R.id.imgMonte);
        ImageView imgMonteVirado = findViewById(R.id.imgMonteVirado);
        AnimatorSet flipFront = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_front);
        AnimatorSet flipBack = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_back);
        Float scale = getApplicationContext().getResources().getDisplayMetrics().density;

        imgMonte.setCameraDistance(8000 * scale);
        imgMonteVirado.setCameraDistance(8000 * scale);

        for (int i = 0; i < qtd; i++) {
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

            //Tira a carta da tela
            ObjectAnimator moveOut = ObjectAnimator.ofFloat(imgMonteVirado, "translationY", 1000f);
            moveOut.setDuration(600);
            moveOut.start();

            Thread.sleep(600);

            //Volta a de costas para cima
            flipFront.setTarget(imgMonteVirado);
            flipBack.setTarget(imgMonte);

            flipBack.start();
            flipFront.start();

            //Adiciona a carta na mão do jogador
            //Remove a carta do deck
            jogador.getDeck().add(cartaVirada);
            jogo.getDeck().remove(cartaVirada);

            //Atualiza as listas
            atualizarListas();

            //Indica que o jogador não pode pedir Uno
            habilitarPedirUno(false);

            cartas.add(cartaVirada);
        }

        //Avisa que o jogador comprou essas cartas
        enviarCartaAutomatica(cartas);
    }

    //Cria animações na tela para compra de cartas na mesa
    private void setAnimacaoComprarCarta() {
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
                //Verifica se é minha vez de jogar
                if (myTurn) {
                    //Verifica se eu deveria ter pedido Uno
                    //Me da duas cartas do monte e avisa
                    if (jogador.getDeck().size() == 1 && !jogador.isUno()) {
                        exibirMensagem("Você esqueceu de pedir Uno.");

                        //Me faz comprar carta automaticamente
                        try {
                            compraAutomatica(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
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
                            myTurn = false;

                            //Adiciona a carta na mão do jogador
                            //Remove a carta do deck
                            jogador.getDeck().add(cartaVirada);
                            jogo.getDeck().remove(cartaVirada);

                            //Avisa que o jogador comprou uma carta
                            enviarCartaComprada(cartaVirada);

                            //Atualiza as listas
                            atualizarListas();

                            //Indica que o jogador não pode pedir Uno
                            habilitarPedirUno(false);
                        }
                    }
                }
            }
        });
    }

    //Distribui as cartas entre os jogadores
    private void entrarNaPartida() throws InterruptedException {
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
                .withType("my-profile")
                .withParam("userId", getIntent().getStringExtra("userId"))
                .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Adiciona o jogador logado no dispositivo
        //Cria a mensagem e envia ao servidor
        msg = new MessageBuilder()
                .withType("join-match")
                .withParam("userId", getIntent().getStringExtra("userId"))
                .withParam("matchId", getIntent().getStringExtra("matchId"))
                .build();

        binder.getService().enviarMensagem(msg);
        Thread.sleep(500);
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

    private void prontoParaJogar() throws InterruptedException {
        ImageView icReady = findViewById(R.id.icReady);
        TextView txtReady = findViewById(R.id.txtReady);

        icReady.setBackgroundResource(R.drawable.ready_to_play);
        txtReady.setTextColor(Color.parseColor("#ED1C24"));

        this.jogador.setStatus(User.UserStatus.READY);

        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
            .withType("ready-to-play")
            .withParam("matchId", getIntent().getStringExtra("matchId"))
            .withParam("userId", getIntent().getStringExtra("userId"))
            .build();

        binder.getService().enviarMensagem(msg);

        layReady.setOnClickListener(null);
    }

    //Ao clicar para pedir uno
    public void pedirUno() {
        this.icPedirUno.setBackgroundResource(R.drawable.mao_uno_selecionado);
        this.txtPedirUno.setTextColor(Color.parseColor("#ED1C24"));

        this.jogador.setIsUno(true);

        //Indica ao servidor que esse jogador pediu Uno
        String msg = new MessageBuilder()
            .withType("ask-uno")
            .withParam("matchId", getIntent().getStringExtra("matchId"))
            .withParam("userId", getIntent().getStringExtra("userId"))
            .build();

        binder.getService().enviarMensagem(msg);
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            entrarNaPartida();                           //Coloca o jogador na partida
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

        User jogador = gson.fromJson(message, User.class);

        if (jogador.getName() != null) {
            this.jogador = jogador;
        } else {
            Match match = gson.fromJson(message, Match.class);

            if (match.getMatchName() != null) {
                this.jogo = match;

                try {
                    criarRecyclerViewJogadores();                //Cria a lista de jogadores na tela
                    criarRecyclerViewCartas();                   //Cria a lista de cartas do jogador na tela
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                TypedMessage msg = gson.fromJson(message, TypedMessage.class);
                User us = null;

                switch (msg.getType()) {
                    //Quando um player enetra na partida
                    case "player-joined":
                        //Insere ele na partida
                        us = gson.fromJson(msg.getContent().toString(), User.class);
                        this.jogo.addPlayer(us);

                        break;
                    case "player-exited":
                        //Quando um player sai da partida
                        us = gson.fromJson(msg.getContent().toString(), User.class);
                        this.jogo.removePlayer(us);

                        break;
                    case "match-started":
                        //Quando a partida começar
                        //O servidor envia a partida
                        match = gson.fromJson(msg.getContent().toString(), Match.class);

                        //Preenche meu deck com as cartas que o servidor separou para mim
                        this.jogador.setDeck(match.getPlayers().get(this.jogador.getUserId()).getDeck());

                        //Atualizo o deck sem essas cartas
                        this.jogo.getDeck().clear();
                        this.jogo.getDeck().addAll(match.getDeck());

                        //Não sabemos a mão dos outros jogadores, somente a quantidade
                        for (User player : this.jogo.getPlayers().values()) {
                            player.setQtdCartas(match.getPlayers().get(player.getUserId()).getDeck().size());
                        }

                        break;
                    case "first-card":
                        //Para gerar a primeira carta na mesa
                        Card cartaMesa = gson.fromJson(msg.getContent().toString(), Card.class);

                        //Remove a carta do deck
                        for (Card c : jogo.getDeck()) {
                            if (c.getImageUrl().contentEquals(cartaMesa.getImageUrl())) {
                                jogo.getDeck().remove(c);

                                break;
                            }
                        }

                        //Atualiza a carta da mesa
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                atualizarCartaMesa(cartaMesa);
                            }
                        });

                        break;
                    case "player-turn":
                        //A cada jogada
                        //Avisa de quem é a vez
                        User usJogada = gson.fromJson(msg.getContent().toString(), User.class);
                        this.userJogada = usJogada.getUserId();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exibirMensagem("Vez de: " + usJogada.getName());
                            }
                        });

                        //Se você for o player da vez
                        //Você pode comprar cartas
                        if (this.jogador.getUserId() == usJogada.getUserId()) {
                            this.myTurn = true;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAnimacaoComprarCarta();
                                }
                            });
                        } else {
                            this.myTurn = false;
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException exc) {
                            exc.printStackTrace();
                        }

                        break;
                    case "card-played":
                        //Quando uma carta é jogada
                        Card carta = gson.fromJson(msg.getContent().toString(), Card.class);

                        //Atualiza a quantidade de cartas do jogador da vez
                        this.jogo.getPlayers().get(userJogada).descartaCarta();

                        //Atualiza a carta da mesa
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                atualizarCartaMesa(carta);
                            }
                        });

                        break;
                    case "card-buyed":
                        //Quando o jogador compra uma carta
                        us = gson.fromJson(msg.getContent().toString(), User.class);

                        //Atualiza o número de cartas desse jogador
                        jogo.getPlayers().get(us.getUserId()).compraCarta();

                        break;
                    case "card-buyed-auto":
                        //Quando o jogador compra várias cartas
                        //Por conta de alguma regra do jogo que puniu ele
                        us = gson.fromJson(msg.getContent().toString(), User.class);

                        //Atualiza o número de cartas desse jogador
                        jogo.getPlayers().get(us.getUserId()).setQtdCartas(us.getDeck().size());

                        break;
                    case "card-shuffled":
                        //Quando as cartas acabarem e precisarem ser reembaralhadas
                        //O servidor envia a partida
                        match = gson.fromJson(msg.getContent().toString(), Match.class);

                        //Atualiza o deck
                        this.jogo.getDeck().clear();
                        this.jogo.getDeck().addAll(match.getDeck());

                        //Atualiza as descartadas
                        this.jogo.getDiscard().clear();
                        this.jogo.getDiscard().addAll(match.getDiscard());

                        break;
                    case "asked-uno":
                        //Quando alguém pedir uno
                        User usUno = gson.fromJson(msg.getContent().toString(), User.class);

                        //Atualiza a carta da mesa
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exibirMensagem(usUno.getName() + " pediu uno. Fica de olho nele!");
                            }
                        });

                        break;
                    case "blocked":
                        //Indica que sua vez foi pulada
                        us = gson.fromJson(msg.getContent().toString(), User.class);

                        //Atualiza a carta da mesa
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exibirMensagem("Sua vez foi pulada.");
                            }
                        });

                        break;
                    case "reverse":
                        //Indica que a ordem do jogo foi invertida
                        //Atualiza a carta da mesa
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                exibirMensagem("A ordem do jogo foi invertida.");
                            }
                        });

                        break;
                    case "+2":
                        //Me faz comprar cartas automaticamente
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    compraAutomatica(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        break;
                    case "match-ended":
                        //Quando a partida termina
                        //Declara-se o ganhador
                        User winner = gson.fromJson(msg.getContent().toString(), User.class);

                        //Inicia a tela de resultados
                        //Enviamos o jogador atual, a partida e o vencedor
                        Intent telaResultados = new Intent(this, TelaResultados.class);
                        telaResultados.putExtra("userId", String.valueOf(this.jogador.getUserId()));
                        telaResultados.putExtra("matchId", String.valueOf(jogo.getMatchId()));
                        telaResultados.putExtra("winnerId", String.valueOf(winner.getUserId()));
                        startActivity(telaResultados);

                        break;
                }

                //Atualiza os adapters
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        atualizarListas();
                    }
                });
            }
        }
    }

}