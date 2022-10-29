package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uno.control.Uno;
import com.example.uno.control.adapter.AdapterCartasJogador;
import com.example.uno.control.adapter.AdapterJogadores;
import com.example.uno.model.Avatar;
import com.example.uno.model.Carta;
import com.example.uno.model.Jogador;
import com.example.uno.model.Jogo;

import java.util.Random;

public class TelaJogo extends AppCompatActivity {

    private Uno uno = Uno.getInstance();
    private Jogo jogo = new Jogo("Jogos da Galera", 4, 1);
    private Jogador jogador = new Jogador("Luis", "1234", new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado));
    private Random random = new Random();

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

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));

        criarRecyclerViewJogadores();
        criarRecyclerViewCartas();
        criarAnimacoes();
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void criarRecyclerViewJogadores() {
        this.jogo.addJogador(new Jogador("Luis", "1234", new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado)));
        this.jogo.addJogador(new Jogador("Gabriel", "1234", new Avatar(R.drawable.avatar_4, R.drawable.avatar_4_selecionado)));
        this.jogo.addJogador(new Jogador("Murilo", "1234", new Avatar(R.drawable.avatar_5, R.drawable.avatar_5_selecionado)));
        this.jogo.addJogador(new Jogador("Giovana", "1234", new Avatar(R.drawable.avatar_2, R.drawable.avatar_2_selecionado)));
        this.jogo.addJogador(new Jogador("Maria", "1234", new Avatar(R.drawable.avatar_6, R.drawable.avatar_6_selecionado)));

        listaJogadores = findViewById(R.id.listaJogadoresJogo);

        listaJogadores.setHasFixedSize(true);

        layManagerJogadores = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaJogadores.setLayoutManager(layManagerJogadores);

        adapterJogadores = new AdapterJogadores(this.jogo);
        listaJogadores.setAdapter(adapterJogadores);
    }

    public void criarRecyclerViewCartas() {
        for (int i = 0; i < 8; i++) {
            //Tira uma carta do baralho para meu deck
            Carta carta = Uno.getInstance().getBaralho().get(random.nextInt(uno.getBaralho().size()));
            Uno.getInstance().getBaralho().remove(carta);

            this.jogador.addCarta(carta);
        }

        listaCartasJogador = findViewById(R.id.listaCartasJogador);

        listaCartasJogador.setHasFixedSize(true);

        layManagerCartasJogador = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaCartasJogador.setLayoutManager(layManagerCartasJogador);

        adapterCartasJogador = new AdapterCartasJogador(this.jogador);
        listaCartasJogador.setAdapter(adapterCartasJogador);
    }

    public void criarAnimacoes() {
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
                    imgMonteVirado.setBackgroundResource(uno.getBaralho().get(random.nextInt(uno.getBaralho().size())).getImg());
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

                    //ADICIONAR NA MAO DO JOGADOR
                }
            }
        });
    }

}