package br.udesc.core.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import br.udesc.core.model.Card;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.server.messages.PlayCardMessage;
import br.udesc.core.server.messages.TypedMessage;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MatchRunner extends Thread {

    private Registry registry = Registry.getInstance();
    private Match match;
    private List<User> players;
    private boolean matchRunning = true;
    private boolean clockWise = true;
    private Gson gson;

    private PlayCardMessage lastPlayCardMessage;

    public MatchRunner(Match match) throws InterruptedException {
        this.match = match;
        this.players = new ArrayList<>();
        this.gson = new Gson();

        // Transforma o Map de players em lista para facilitar as nossas operações
        for (User u : this.match.getPlayers().values()) {
            this.players.add(u);
        }
    }

    @Override
    public void run() {
        int jogadorAtual = 0;

        while (matchRunning) {
            // Primeiro jogador a jogar
            User user = this.match.getPlayers().get(jogadorAtual);
            sendMessageToAllPlayers(new TypedMessage("player-turn", user));

            try {
                Awaitility.await().atMost(Duration.ofSeconds(10)).until(doesPlayerPlayCard(user.getId()));
                Card c = lastPlayCardMessage.getCardPlayed();
                lastPlayCardMessage = null;

                sendMessageToAllPlayers(new TypedMessage("card-played", c));
                match.getPlayers().get(user.getId()).popCarta(c);

                jogadorAtual = getProximoPlayer(jogadorAtual);

            } catch (ConditionTimeoutException e) {
                // Pula pro próximo jogador da lista
                jogadorAtual = getProximoPlayer(jogadorAtual);
            }

        }
    }

    public void onPlayCardMessage(PlayCardMessage message) {
        this.lastPlayCardMessage = message;
    }

    private Callable<Boolean> doesPlayerPlayCard(int playerId) {
        return () -> {
            if (lastPlayCardMessage != null) {
                return lastPlayCardMessage.getUserId() == playerId;
            }
            return false;
        };
    }

    // Recupera o próximo player que deve jogar
    private int getProximoPlayer(int posAtual) {
        if (posAtual == (this.players.size() - 1)) {
            return 0;
        } else {
            return posAtual + 1;
        }
    }

    // Envia uma mensagem para todos
    private void sendMessageToAllPlayers(TypedMessage msg) {
        for (User us : this.match.getPlayers().values()) {
            MessageBroker.getInstance().sendMessageToUser(us.getId(), gson.toJson(msg));
        }
    }

}
