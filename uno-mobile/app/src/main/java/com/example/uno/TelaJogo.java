package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.uno.control.AdapterCartasJogador;
import com.example.uno.control.AdapterJogadores;

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
        listaJogadores = findViewById(R.id.listaJogadoresJogo);
        listaCartasJogador = findViewById(R.id.listaCartasJogador);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));

        listaJogadores.setHasFixedSize(true);
        listaCartasJogador.setHasFixedSize(true);

        layManagerJogadores = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaJogadores.setLayoutManager(layManagerJogadores);
        layManagerCartasJogador = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaCartasJogador.setLayoutManager(layManagerCartasJogador);

        adapterJogadores = new AdapterJogadores();
        listaJogadores.setAdapter(adapterJogadores);
        adapterCartasJogador = new AdapterCartasJogador();
        listaCartasJogador.setAdapter(adapterCartasJogador);
    }
}