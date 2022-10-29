package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.AdapterJogos;
import com.example.uno.model.Jogo;
import com.example.uno.model.Servidor;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        Servidor servidor = new Servidor("127.0.0.1", "80");

        servidor.addJogo(new Jogo("Jogos da Galera", 4, 1));
        servidor.addJogo(new Jogo("Joga comigo", 4, 2));
        servidor.addJogo(new Jogo("Chega mais", 4, 4));
        servidor.addJogo(new Jogo("Vem pra diversão", 4, 3));

        TextView txtDescListaJogos = findViewById(R.id.txtDescListaJogos);
        txtDescListaJogos.setText("Jogos ativos em: " + servidor.getIP());

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