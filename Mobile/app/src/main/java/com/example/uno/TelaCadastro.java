package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.uno.control.AdapterAvatares;

public class TelaCadastro extends AppCompatActivity {

    private RecyclerView listaAvatares;
    private RecyclerView.Adapter adapterUsuarios;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        ImageView icVoltar = findViewById(R.id.icSair);

        listaAvatares = findViewById(R.id.listaAvatares);
        listaAvatares.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaAvatares.setLayoutManager(layoutManager);

        adapterUsuarios = new AdapterAvatares(listaAvatares);
        listaAvatares.setAdapter(adapterUsuarios);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_horizontal));
        listaAvatares.addItemDecoration(divisor);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));
    }
}