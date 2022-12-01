package testes;

import static org.junit.Assert.assertNotNull;

import com.example.uno.control.socket.ClientSocket;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.model.Avatar;
import com.example.uno.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@RunWith(JUnit4.class)
public class TestesCadastro {

    private Gson gson;
    private ClientSocket socket;
    private String message;

    @Before
    public void antes() {
        //Cria o objeto para manipulação das mensagens
        this.gson = new Gson();

        //Inicia conexão com o server
        try {
            if (socket == null) {
                createSocketClient("192.168.3.34:2000");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                TestesCadastro.this.message = message;
            }
        });

        //Inicia o socket
        socket.start();
    }

    //Testa se os avatares estão vindo do server
    @Test
    public void testAvatars() throws InterruptedException {
        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("get-avatars")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em uma lista do tipo Avatar
        Type listType = new TypeToken<Map<Integer, Avatar>>(){}.getType();
        Map<Integer, Avatar> listaConvertida = gson.fromJson(json, listType);

        assertNotNull(listaConvertida.get(1));
    }

    //Testa se os avatares estão vindo do server
    @Test
    public void testeCadastrar() throws InterruptedException {
        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("sign-up")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .withParam("avatarId", "1")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        User user = gson.fromJson(json, User.class);

        assertNotNull(user);
    }

    //Finaliza a conexão após o teste terminar
    @After
    public void fim() {
        socket.killThread();
    }
}
