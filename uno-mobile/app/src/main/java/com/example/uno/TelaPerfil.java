package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.model.Avatar;
import com.example.uno.model.Jogador;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        ImageView icSair = findViewById(R.id.icSair);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaServidores.class)));

        Jogador jogador = new Jogador("Luis Felipe", "1234", new Avatar(R.drawable.avatar_1, R.drawable.avatar_1_selecionado));
        preencherPerfil(jogador);
    }

    public void preencherPerfil(Jogador jogador) {
        ImageView imgAvatarPerfil = findViewById(R.id.imgAvatarPerfil);
        TextView txtNome = findViewById(R.id.txtDescListaJogos);
        TextView txtJogos = findViewById(R.id.txtPerfilJogos);
        TextView txtVitorias = findViewById(R.id.txtPerfilVitorias);

        imgAvatarPerfil.setBackgroundResource(jogador.getAvatar().getImgSelecionado());
        txtNome.setText(jogador.getNome());
        txtJogos.setText(String.valueOf(jogador.getJogos()) + " Jogos");
        txtVitorias.setText(String.valueOf(jogador.getVitorias()) + " Vitórias");
    }
}