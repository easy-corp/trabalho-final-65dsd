package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uno.control.adapter.AdapterAvatares;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.control.socket.ServiceSocket;
import com.example.uno.model.Avatar;
import com.example.uno.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity implements ServiceConnection, IMessageListener {

    private ServiceConnection service;
    private String message;
    private ServiceSocket.LocalBinder binder;
    private Gson gson;

    private RecyclerView listaAvatares;
    private AdapterAvatares adapterUsuarios;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        this.gson = new Gson();
        this.service = this;

        //Binda o serviço nessa Activity
        bindService(new Intent(this, ServiceSocket.class), service, 0);

        ImageView icVoltar = findViewById(R.id.icSair);
        EditText edNome = findViewById(R.id.edNome);
        EditText edSenha = findViewById(R.id.edSenha);
        EditText edConfSenha = findViewById(R.id.edConfSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaLogin.class)));

        btnCadastrar.setOnClickListener(param -> {
            try {
                realizarCadastro(edNome.getText().toString(), edSenha.getText().toString(), edConfSenha.getText().toString(), this.adapterUsuarios.getPosSelecionado());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(service);
        super.onDestroy();
    }

    public void criarRecyclerView() throws InterruptedException {
        listaAvatares = findViewById(R.id.listaAvatares);
        listaAvatares.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaAvatares.setLayoutManager(layoutManager);

        //Pega a lista de avatares do servidor;
        String msg = new MessageBuilder()
            .withType("get-avatars-list")
            .build();

        binder.getService().enviarMensagem(msg);

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        System.out.println(json);

        //Transforma a lista vinda em Gson em um mapa de objetos Avatar
        //Transforma o Gson novamente em uma lista do tipo Avatar
        Type listType = new TypeToken<Map<Integer, Avatar>>(){}.getType();
        Map<Integer, Avatar> avatares = gson.fromJson(json, listType);

        adapterUsuarios = new AdapterAvatares(listaAvatares, avatares);
        listaAvatares.setAdapter(adapterUsuarios);

        DividerItemDecoration divisor = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        divisor.setDrawable(getDrawable(R.drawable.divisor_horizontal));
        listaAvatares.addItemDecoration(divisor);
    }

    public void realizarCadastro(String nome, String senha, String confSenha, int avatarId) throws InterruptedException {
        if (nome.isEmpty() || senha.isEmpty() || confSenha.isEmpty()) {
            exibirMensagem("Você precisa preencher todos os campos para realizar o cadastro.");
        } else {
            if (!senha.contentEquals(confSenha)) {
                exibirMensagem("As senhas não coincidem.");
            } else {
                //Cria a mensagem e envia ao servidor
                String msg = new MessageBuilder()
                    .withType("sign-up")
                    .withParam("username", nome)
                    .withParam("password", senha)
                    .withParam("avatarId", String.valueOf(avatarId + 1))
                    .build();

                binder.getService().enviarMensagem(msg);

                Thread.sleep(500);

                //Valor retornado pelo server
                String json = this.message;

                //Transforma o Gson novamente em um tipo User
                User user = gson.fromJson(json, User.class);

                if (user == null) {
                    exibirMensagem("Ocorreu algo errado com seu cadastro, tente novamente mais tarde.");
                } else {
                    startActivity(new Intent(this, TelaLogin.class));
                }
            }
        }
    }
    
    public void exibirMensagem(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //Quando o serviço for bindado
    //Indica que essa activity irá ouvir as mensagens
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.binder = (ServiceSocket.LocalBinder) iBinder;
        this.binder.addListener(this);

        try {
            criarRecyclerView();
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