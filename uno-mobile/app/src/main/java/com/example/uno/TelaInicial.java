package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edIP = findViewById(R.id.edUsuario);
        EditText edPorta = findViewById(R.id.edSenha);
        Button btnConectar = findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(param -> conectar(edIP.getText().toString(), edPorta.getText().toString()));
    }

    private void conectar(String ip, String porta) {
        if (ip.isEmpty() || porta.isEmpty()) {
            exibirMensagem("Você precisa inserir usuário e senha para poder se conectar.");
        } else {
            if (ip.contains(".")) {
                startActivity(new Intent(this, TelaLogin.class));
            } else {
                exibirMensagem("IP inválido.");
            }
        }
    }

    private void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}