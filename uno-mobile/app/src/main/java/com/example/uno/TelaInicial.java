package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnTestesSocket = findViewById(R.id.btnTesteSocket);

        btnEntrar.setOnClickListener(param -> startActivity(new Intent(this, TelaServidores.class)));


        btnCadastrar.setOnClickListener(param -> startActivity(new Intent(this, TelaCadastro.class)));

        btnTestesSocket.setOnClickListener(param -> startActivity(new Intent(this, TelaTestesSocket.class)));

    }
}