package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.uno.control.AdapterUsuarios;

import java.util.ArrayList;
import java.util.List;

public class TelaCadastro extends AppCompatActivity {

    private RecyclerView listaAvatares;
    private RecyclerView.Adapter adapterUsuarios;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        ImageView icVoltar = findViewById(R.id.icVoltar);

        listaAvatares = findViewById(R.id.listaAvatares);
        listaAvatares.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        listaAvatares.setLayoutManager(layoutManager);

        List<String> teste = new ArrayList<>();
        teste.add("Avatar1");
        teste.add("Avatar2");
        teste.add("Avatar3");
        teste.add("Avatar4");

        adapterUsuarios = new AdapterUsuarios(teste);
        listaAvatares.setAdapter(adapterUsuarios);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));
    }
}