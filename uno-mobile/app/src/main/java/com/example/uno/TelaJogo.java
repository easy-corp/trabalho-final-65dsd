package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uno.control.adapter.AdapterCartasJogador;
import com.example.uno.control.adapter.AdapterJogadores;
import com.example.uno.model.Avatar;
import com.example.uno.model.Card;
import com.example.uno.model.User;
import com.example.uno.model.Match;

import java.util.Random;

public class TelaJogo extends AppCompatActivity {

    private Match jogo = new Match("Jogos da Galera", 4);
    private User jogador = new User("Luis", "1234",
            new Avatar("avatar_1"));
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

        ImageView icSair = findViewById(R.id.icSair);
        FrameLayout layUno = findViewById(R.id.layUno);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));
        layUno.setOnClickListener(param -> pedirUno());

        distribuiCartas();
        criarRecyclerViewJogadores();
        criarRecyclerViewCartas();
        comprarCarta();
        gerarCartaMesa();
    }

    public Match getJogo() {
        return this.jogo;
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Gera lista de jogadores na sala
    private void criarRecyclerViewJogadores() {
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
    private void distribuiCartas() {
        //Adiciona o jogador logado no dispositivo
        this.jogo.addPlayer(this.jogador);

        //Adiciona demais jogadores
        this.jogo.addPlayer(new User("Gabriel", "1234", new Avatar("avatar_4")));
        this.jogo.addPlayer(new User("Murilo", "1234", new Avatar("avatar_5")));
        this.jogo.addPlayer(new User("Giovana", "1234", new Avatar("avatar_2")));
        this.jogo.addPlayer(new User("Maria", "1234", new Avatar("avatar_6")));

        for (User j : this.jogo.getPlayers()) {
            for (int i = 0; i < 7; i++) {
                //Tira uma carta do baralho para meu deck
                Card carta = this.jogo.getDeck().get(random.nextInt(jogo.getDeck().size()));
                this.jogo.getDeck().remove(carta);

                j.addCarta(carta);
            }
        }
    }

    //Atualizar as listas na tela quando os dados mudarem
    public void atualizarListas() {
        adapterJogadores.notifyDataSetChanged();
        adapterCartasJogador.notifyDataSetChanged();
    }

    public void pedirUno() {
        ImageView icPedirUno = findViewById(R.id.icPedirUno);
        TextView txtPedirUno = findViewById(R.id.txtPedirUno);

        icPedirUno.setBackgroundResource(R.drawable.mao_uno_selecionado);
        txtPedirUno.setTextColor(Color.parseColor("#ED1C24"));

        this.jogador.setIsUno(true);
    }

}