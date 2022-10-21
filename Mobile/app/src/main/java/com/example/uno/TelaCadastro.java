package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class TelaCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        ImageView icVoltar = findViewById(R.id.icVoltar);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));
    }
}