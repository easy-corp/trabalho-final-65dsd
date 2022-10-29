package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TelaServidores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_servidores);

        ImageView icVoltar = findViewById(R.id.icSair);
        ImageView icUsuario = findViewById(R.id.icUsuario);
        EditText edIP = findViewById(R.id.edIP);
        EditText edPorta = findViewById(R.id.edPorta);
        Button btnEntrarServidor = findViewById(R.id.btnEntrarServidor);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));

        btnEntrarServidor.setOnClickListener(param -> entrarServidor(edIP.getText().toString(), edPorta.getText().toString()));
    }

    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void entrarServidor(String ip, String porta) {
        if (ip.isEmpty() || porta.isEmpty()) {
            exibirMensagem("Preencha todos os dados para entrar no servidor.");
        } else {
            if (ip.contains(".")) {
                startActivity(new Intent(this, TelaEntrarJogo.class));
            } else {
                exibirMensagem("IP inválido.");
            }
        }
    }

}