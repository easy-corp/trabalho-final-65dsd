package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class TelaEntrarJogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrar_jogo);

        ImageView icVoltar = findViewById(R.id.icVoltar);
        ImageView icUsuario = findViewById(R.id.icUsuario);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));
    }
}