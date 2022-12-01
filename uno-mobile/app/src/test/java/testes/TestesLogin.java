package testes;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.uno.control.socket.ClientSocket;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.model.User;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

@RunWith(JUnit4.class)
public class TestesLogin {

    private Gson gson;
    private ClientSocket socket;
    private String message;

    @Before
    public void antes() throws InterruptedException {
        //Cria o objeto para manipulação das mensagens
        this.gson = new Gson();

        //Inicia conexão com o server
        try {
            if (socket == null) {
                createSocketClient(Config.getInstance().getIp());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Faz signup para poder receber login depois
        socket.sendMessage(
            new MessageBuilder()
                .withType("sign-up")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .withParam("avatarId", "1")
                .build()
        );

        Thread.sleep(500);
    }

    //Cria o socket
    private void createSocketClient(String serverAddress) throws IOException {
        //Recupera o ip e porta vindo como string
        String[] parts = serverAddress.split(":");
        String ip = parts[0];
        int port = Integer.parseInt(parts[1]);

        //Cria um novo socket e deixa um listener esperando a mensagem vir
        socket = new ClientSocket(ip, port, new IMessageListener() {
            @Override
            public void onMessage(String message) {
                TestesLogin.this.message = message;
            }
        });

        //Inicia o socket
        socket.start();
    }

    //Teste o login com usuário valido
    @Test
    public void testeLoginValido() throws InterruptedException {
        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("login")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        User user = gson.fromJson(json, User.class);

        assertNotNull(user);
    }

    //Teste o login com usuário invalido
    @Test
    public void testeLoginInvalido() throws InterruptedException {
        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("login")
                .withParam("username", "murilo")
                .withParam("password", "4321")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        User user = gson.fromJson(json, User.class);

        assertNull(user);
    }

    //Finaliza a conexão após o teste terminar
    @After
    public void fim() {
        socket.killThread();
    }

}
