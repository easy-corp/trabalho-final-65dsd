package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.uno.control.AdapterJogos;

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
        listaJogos = findViewById(R.id.listaJogos);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));

        listaJogos.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listaJogos.setLayoutManager(layoutManager);

        adapterJogos = new AdapterJogos(this);
        listaJogos.setAdapter(adapterJogos);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_vertical));
        listaJogos.addItemDecoration(divisor);
    }
}