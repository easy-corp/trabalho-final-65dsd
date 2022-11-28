package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uno.control.adapter.AdapterAvatares;

public class TelaCadastro extends AppCompatActivity {

    private RecyclerView listaAvatares;
    private RecyclerView.Adapter adapterUsuarios;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        ImageView icVoltar = findViewById(R.id.icSair);
        EditText edNome = findViewById(R.id.edNome);
        EditText edSenha = findViewById(R.id.edSenha);
        EditText edConfSenha = findViewById(R.id.edConfSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaLogin.class)));

        btnCadastrar.setOnClickListener(param -> realizarCadastro(edNome.getText().toString(), edSenha.getText().toString(), edConfSenha.getText().toString()));

        criarRecyclerView();
    }

    public void criarRecyclerView() {
        listaAvatares = findViewById(R.id.listaAvatares);
        listaAvatares.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaAvatares.setLayoutManager(layoutManager);

        adapterUsuarios = new AdapterAvatares(listaAvatares);
        listaAvatares.setAdapter(adapterUsuarios);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_horizontal));
        listaAvatares.addItemDecoration(divisor);
    }

    public void realizarCadastro(String nome, String senha, String confSenha) {
        if (nome.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
            exibirMensagem("Você precisa preencher todos os campos para realizar o cadastro.");
        } else {
            if (!senha.contentEquals(confSenha)) {
                exibirMensagem("As senhas não coincidem.");
            } else {
                //REALIZAR CADASTRO

                startActivity(new Intent(this, TelaInicial.class));
            }
        }
    }
    
    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}