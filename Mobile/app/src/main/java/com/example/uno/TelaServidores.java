package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class TelaServidores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_servidores);

        ImageView icVoltar = findViewById(R.id.icVoltar);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));
    }
}