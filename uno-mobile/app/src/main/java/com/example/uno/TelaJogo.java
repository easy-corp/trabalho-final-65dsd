package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.uno.control.AdapterCartasJogador;
import com.example.uno.control.AdapterJogadores;
import com.example.uno.model.Avatar;
import com.example.uno.model.Carta;
import com.example.uno.model.Jogador;
import com.example.uno.model.Jogo;

import java.util.ArrayList;

public class TelaJogo extends AppCompatActivity {

    private RecyclerView listaJogadores;
    private RecyclerView listaCartasJogador;
    private RecyclerView.Adapter adapterJogadores;
    private RecyclerView.Adapter adapterCartasJogador;
    private RecyclerView.LayoutManager layManagerJogadores;
    private RecyclerView.LayoutManager layManagerCartasJogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_jogo);

        ImageView icSair = findViewById(R.id.icSair);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));

        criarRecyclerViewJogadores();
        criarRecyclerViewCartas();
    }

    public void criarRecyclerViewJogadores() {
        Jogo jogo = new Jogo("Jogos da Galera", 4, 1);

        jogo.addJogador(new Jogador("Luis", "1234", new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado)));
        jogo.addJogador(new Jogador("Gabriel", "1234", new Avatar(R.drawable.avatar_4, R.drawable.avatar_4_selecionado)));
        jogo.addJogador(new Jogador("Murilo", "1234", new Avatar(R.drawable.avatar_5, R.drawable.avatar_5_selecionado)));
        jogo.addJogador(new Jogador("Giovana", "1234", new Avatar(R.drawable.avatar_2, R.drawable.avatar_2_selecionado)));
        jogo.addJogador(new Jogador("Maria", "1234", new Avatar(R.drawable.avatar_6, R.drawable.avatar_6_selecionado)));

        listaJogadores = findViewById(R.id.listaJogadoresJogo);

        listaJogadores.setHasFixedSize(true);

        layManagerJogadores = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaJogadores.setLayoutManager(layManagerJogadores);

        adapterJogadores = new AdapterJogadores(jogo);
        listaJogadores.setAdapter(adapterJogadores);
    }

    public void criarRecyclerViewCartas() {
        Jogador jogador = new Jogador("Luis", "1234", new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado));

        jogador.addCarta(new Carta("1", "RED", R.drawable.red_1));
        jogador.addCarta(new Carta("4", "BLUE", R.drawable.blue_4));
        jogador.addCarta(new Carta("4", "BLUE", R.drawable.blue_4));
        jogador.addCarta(new Carta("8", "GREEN", R.drawable.green_8));
        jogador.addCarta(new Carta("1", "RED", R.drawable.red_1));
        jogador.addCarta(new Carta("8", "GREEN", R.drawable.green_8));
        jogador.addCarta(new Carta("8", "GREEN", R.drawable.green_8));
        jogador.addCarta(new Carta("4", "BLUE", R.drawable.blue_4));

        listaCartasJogador = findViewById(R.id.listaCartasJogador);

        listaCartasJogador.setHasFixedSize(true);

        layManagerCartasJogador = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaCartasJogador.setLayoutManager(layManagerCartasJogador);

        adapterCartasJogador = new AdapterCartasJogador(jogador);
        listaCartasJogador.setAdapter(adapterCartasJogador);
    }
}