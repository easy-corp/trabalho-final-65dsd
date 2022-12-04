package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.textclassifier.TextLanguage;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Avatar;
import com.example.uno.model.User;
import com.google.gson.Gson;

public class TelaPerfil extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;
    private User jogador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icSair = findViewById(R.id.icSair);

        icSair.setOnClickListener(param -> startActivity(new Intent(this, TelaEntrarJogo.class).putExtra("userId", String.valueOf(jogador.getId()))));
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    public void preencherPerfil() throws InterruptedException {
        //Cria a mensagem e envia ao servidor
        String msg = new MessageBuilder()
            .withType("my-profile")
            .withParam("userId", this.getIntent().getStringExtra("userId"))
            .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        User user = gson.fromJson(json, User.class);
        this.jogador = user;

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

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            preencherPerfil();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Quando o serviço for desbindado
    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        this.service = null;
    }

    //Quando a tela pausar
    @Override
    public void onPause() {
        super.onPause();
        if (binder != null) {
            binder.removeListener(this);
        }
    }

    //Quando a tela voltar
    @Override
    public void onResume() {
        super.onResume();
        if (binder != null) {
            binder.addListener(this);
        }
    }

    //Esperando mensagens
    @Override
    public void onMessage(String message) {
        this.message = message;
    }

}