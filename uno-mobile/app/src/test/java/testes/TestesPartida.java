package testes;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.uno.control.socket.ClientSocket;
import com.example.uno.control.socket.IMessageListener;
import com.example.uno.control.socket.MessageBuilder;
import com.example.uno.model.Match;
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
public class TestesPartida {

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
                createSocketClient(Config.getInstance().getIp());
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
                TestesPartida.this.message = message;
            }
        });

        //Inicia o socket
        socket.start();
    }

    //Teste criar uma partida
    @Test
    public void testeCriarPartida() throws InterruptedException {
        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("create-match")
                .withParam("name", "Jogos da Galera")
                .withParam("qtdPlayers", "4")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        Match match = gson.fromJson(json, Match.class);

        assertNotNull(match);
    }

    //Teste recuperar as partidas existentes
    @Test
    public void testeListarPartidas() throws InterruptedException {
        //Cria uma partida para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("create-match")
                .withParam("name", "Jogos da Galera")
                .withParam("qtdPlayers", "4")
                .build()
        );

        Thread.sleep(500);

        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("get-matches-list")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        Type listType = new TypeToken<Map<Integer, Match>>(){}.getType();
        Map<Integer, Match> matches = gson.fromJson(json, listType);

        System.out.println(matches.size());

        for (Match m : matches.values()) {
            System.out.println(m.getName());
        }

        //Verifica se há partidas criadas
        assertTrue(matches.size() > 0);
    }

    //Teste entrar em uma partida
    @Test
    public void testeEntrarPartida() throws InterruptedException {
        //Cria uma partida para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("create-match")
                .withParam("name", "Jogos da Galera")
                .withParam("qtdPlayers", "4")
                .build()
        );

        Thread.sleep(500);

        //Cria um usuário para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("sign-up")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .withParam("avatarId", "1")
                .build()
        );

        Thread.sleep(500);

        //Coloca o usuário na partida
        socket.sendMessage(
            new MessageBuilder()
                .withType("join-match")
                .withParam("userId", "1")
                .withParam("matchId", "1")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo Match
        Match match = gson.fromJson(json, Match.class);

        //Verifica se o usuário está lá dentro
        assertNotNull(match.getPlayers().get(1));
    }

    //Teste sair de uma partida
    @Test
    public void testeSairPartida() throws InterruptedException {
        //Cria uma partida para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("create-match")
                .withParam("name", "Jogos da Galera")
                .withParam("qtdPlayers", "4")
                .build()
        );

        Thread.sleep(500);

        //Cria um usuário para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("sign-up")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .withParam("avatarId", "1")
                .build()
        );

        Thread.sleep(500);

        //Coloca o usuário na partida
        socket.sendMessage(
            new MessageBuilder()
                .withType("join-match")
                .withParam("userId", "1")
                .withParam("matchId", "1")
                .build()
        );

        Thread.sleep(500);

        //Guarda a partida para poder comparar depois
        String json = this.message;
        Match matchAntes = gson.fromJson(json, Match.class);

        //Retira o usuário da partida
        socket.sendMessage(
            new MessageBuilder()
                .withType("quit-match")
                .withParam("userId", "1")
                .withParam("matchId", "1")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        json = this.message;

        //Transforma o Gson novamente em um tipo Match
        Match matchDepois = gson.fromJson(json, Match.class);

        System.out.println(matchAntes.getPlayers().size());
        System.out.println(matchDepois.getPlayers().size());

        //Verifica se o número de usuários diminui
        //Significa que ele foi retirado de lá
        assertTrue(matchAntes.getPlayers().size() > matchDepois.getPlayers().size());
    }

    //Teste usuário pronto para jogar
    @Test
    public void testeProntoJogar() throws InterruptedException {
        //Cria uma partida para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("create-match")
                .withParam("name", "Jogos da Galera")
                .withParam("qtdPlayers", "4")
                .build()
        );

        Thread.sleep(500);

        //Cria um usuário para teste
        socket.sendMessage(
            new MessageBuilder()
                .withType("sign-up")
                .withParam("username", "murilo")
                .withParam("password", "1234")
                .withParam("avatarId", "1")
                .build()
        );

        Thread.sleep(500);

        //Coloca o usuário na partida
        socket.sendMessage(
            new MessageBuilder()
                .withType("join-match")
                .withParam("userId", "1")
                .withParam("matchId", "1")
                .build()
        );

        Thread.sleep(500);

        //Envia a mensagem para o server
        socket.sendMessage(
            new MessageBuilder()
                .withType("ready-to-play")
                .withParam("userId", "1")
                .withParam("matchId", "1")
                .build()
        );

        Thread.sleep(500);

        //Valor retornado pelo server
        String json = this.message;

        //Transforma o Gson novamente em um tipo User
        User user = gson.fromJson(json, User.class);

        //Verifica se o Status desse usuário está como READY
        assertTrue(user.getStatus() == User.UserStatus.READY);
    }

    //Finaliza a conexão após o teste terminar
    @After
    public void fim() {
        socket.killThread();
    }

}
