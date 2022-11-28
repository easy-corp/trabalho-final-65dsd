package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.textclassifier.TextLanguage;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.model.Avatar;
import com.example.uno.model.User;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        ImageView icSair = findViewById(R.id.icSair);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class)));

        User jogador = new User("Luis", "1234", new Avatar("avatar_1"));
        preencherPerfil(jogador);
    }

    public void preencherPerfil(User jogador) {
        ImageView imgAvatarPerfil = findViewById(R.id.imgAvatarPerfil);
        TextView txtNome = findViewById(R.id.txtPerfilNome);
        TextView txtJogos = findViewById(R.id.txtPerfilJogos);
        TextView txtVitorias = findViewById(R.id.txtPerfilVitorias);

        jogador.getAvatar().click(true);
        int image = getResources().getIdentifier(jogador.getAvatar().getImageUrl(), "drawable", getPackageName());
        imgAvatarPerfil.setBackgroundResource(image);
        txtNome.setText(jogador.getName());

        /////////////Verificar/////////////
        txtJogos.setText(String.valueOf("5 Jogos"));
        txtVitorias.setText(String.valueOf("5 Vitórias"));
    }
}