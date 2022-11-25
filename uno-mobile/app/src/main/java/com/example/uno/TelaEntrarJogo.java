package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.adapter.AdapterJogos;
import com.example.uno.model.Match;
import com.example.uno.model.GameServer;

public class TelaEntrarJogo extends AppCompatActivity {

    private RecyclerView listaJogos;
    private RecyclerView.Adapter adapterJogos;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrar_jogo);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaServidores.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));

        criarRecyclerView();
    }

    public void criarRecyclerView() {
        GameServer servidor = new GameServer("127.0.0.1", 80);

        servidor.addMatch(new Match("Jogos da Galera", 4));
        servidor.addMatch(new Match("Joga comigo", 4));
        servidor.addMatch(new Match("Chega mais", 4));
        servidor.addMatch(new Match("Vem pra diversão", 4));

        TextView txtDescListaJogos = findViewById(R.id.txtDescListaJogos);
        txtDescListaJogos.setText("Jogos ativos em: " + servidor.getIp());

        listaJogos = findViewById(R.id.listaJogos);

        listaJogos.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaJogos.setLayoutManager(layoutManager);

        adapterJogos = new AdapterJogos(this, servidor);
        listaJogos.setAdapter(adapterJogos);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_vertical));
        listaJogos.addItemDecoration(divisor);
    }
}