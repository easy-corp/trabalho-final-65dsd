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

        EditText edUsuario = findViewById(R.id.edUsuario);
        EditText edSenha = findViewById(R.id.edSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnTestesSocket = findViewById(R.id.btnTesteSocket);

        btnEntrar.setOnClickListener(param -> login(edUsuario.getText().toString(), edSenha.getText().toString()));

        btnCadastrar.setOnClickListener(param -> startActivity(new Intent(this, TelaCadastro.class)));

        btnTestesSocket.setOnClickListener(param -> startActivity(new Intent(this, TelaTestesSocket.class)));
    }

    private void login(String usuario, String senha) {
        if (usuario.isEmpty() || senha.isEmpty()) {
            exibirMensagem("Você precisa inserir usuário e senha para poder realizar o login.");
        } else {
            if (usuario.contentEquals("Luis") && senha.contentEquals("1234")) {
                startActivity(new Intent(this, TelaServidores.class));
            } else {
                exibirMensagem("Usuário ou senha inválidos!");
            }
        }
    }

    private void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}