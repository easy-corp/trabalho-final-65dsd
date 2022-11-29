package com.example.uno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.uno.control.socket.ClientSocket;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.io.IOException;


public class TelaTestesSocket extends AppCompatActivity {

    private ClientSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_testes_socket);

        ImageView icVoltar = findViewById(R.id.icVoltarTesteSocket);
        ImageView icUsuario = findViewById(R.id.icUsuarioTesteSocket);
        Button btnEnviarMensagemSocket = findViewById(R.id.btnEnviarMensagemSocket);
        TextInputEditText adress = findViewById(R.id.enderecoServerSocket);
        TextInputEditText message = findViewById(R.id.mensagemSocket);

        icVoltar.setOnClickListener(param -> startActivity(new Intent(this, TelaInicial.class)));

        icUsuario.setOnClickListener(param -> startActivity(new Intent(this, TelaPerfil.class)));

        btnEnviarMensagemSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (socket == null) {
                        createSocketClient(adress.getText().toString());
                    }

                    socket.sendMessage(
                        new MessageBuilder()
                        .withType("signup")
                        .withParam("username", "murilo")
                        .withParam("password", "123")
                        .withParam("avatarId", "1")
                        .build()
                    );

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createSocketClient(String serverAddress) throws IOException {
        String[] parts = serverAddress.split(":");
        String ip = parts[0];
        int port = Integer.parseInt(parts[1]);
        socket = new ClientSocket(ip, port, new IMessageListener() {
            @Override
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
        socket.start();
    }
}