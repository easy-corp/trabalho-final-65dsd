package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        ImageView icVoltar = findViewById(R.id.icVoltar);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaServidores.class)));
    }
}