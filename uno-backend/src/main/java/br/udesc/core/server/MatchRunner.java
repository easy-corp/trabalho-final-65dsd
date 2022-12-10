package br.udesc.core.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import br.udesc.core.model.Card;
import br.udesc.core.model.Match;
import br.udesc.core.model.User;
import br.udesc.core.server.messages.BuyCardMessage;
import br.udesc.core.server.messages.PlayCardMessage;
import br.udesc.core.server.messages.TypedMessage;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.hamcrest.core.AnyOf;

import com.google.gson.Gson;

public class MatchRunner extends Thread {

    private Match match;                           // A partida que está acontecendo
    private List<User> players;                    // Uma lista de usuários que define a ordem de jogadas
    private boolean matchRunning = true;           // Atributo que define que a partida está acontecendo
    private PlayCardMessage lastPlayCardMessage;   // A última carta jogada
    private BuyCardMessage lastBuyCardMessage;     // A última carta comprada
    private Gson gson;
    private Random random;

    public MatchRunner(Match match) throws InterruptedException {
        this.match = match;
        this.players = new ArrayList<>();
        this.gson = new Gson();
        this.random = new Random();

        // Transforma o Map de players em lista para facilitar as nossas operações
        for (User u : this.match.getPlayers().values()) {
            this.players.add(u);
        }
    }

    @Override
    public void run() {
        int jogadorAtual = 0;

        //Gera a carta inicial na mesa
        gerarCartaMesa();

        while (matchRunning) {
            // Primeiro jogador a jogar
            // Avisa os demais que é a vez dele
            User user = this.match.getPlayers().get(jogadorAtual);
            sendMessageToAllPlayers(new TypedMessage("player-turn", user));

            try {
                // Aguarda o jogador jogar ou comprar uma carta, por um máximo de segundos
                Awaitility.await()
                    .atMost(Duration.ofSeconds(30))
                    .until(doesPlayerPlay(user.getId()));

                if (lastPlayCardMessage != null) { //Se o jogador jogou uma carta
                    // Pega a última carta jogada pelo players
                    User us = this.match.getPlayers().get(lastPlayCardMessage.getUserId());
                    Card c = lastPlayCardMessage.getCardPlayed();
                    lastPlayCardMessage = null;

                    // Avisa todo mundo a carta que foi jogada
                    sendMessageToAllPlayers(new TypedMessage("card-played", c));

                    // Tira a carta da mão do player que jogou
                    us.popCarta(c);
                    
                    // Coloca ela na lista de cartas descartadas
                    match.addDiscard(c);
                } else { //Se o jogador comprou uma carta
                    // Adiciona essa carta na mão do jogador
                    User us = this.match.getPlayers().get(lastBuyCardMessage.getUserId());
                    Card c = lastBuyCardMessage.getCardBuyed();
                    lastBuyCardMessage = null;

                    //Adiciona a carta na mão do jogador
                    us.getDeck().add(c);

                    // Avisa todo mundo que o jogador comprou uma carta
                    // Os demais jogadores não precisam saber qual carta foi
                    sendMessageToAllPlayers(new TypedMessage("card-buyed", us));
                    
                }

                // Pula pro próximo jogador da lista
                jogadorAtual = getProximoPlayer(jogadorAtual);
            } catch (ConditionTimeoutException e) {
                // Pula pro próximo jogador da lista
                jogadorAtual = getProximoPlayer(jogadorAtual);
            }

            for (User p : match.getPlayers().values()) {
                System.out.println("O " + p.getName() + " tem " + p.getDeck().size() + " cartas.");
            }
            
            // Verifica se a partida terminou
            // Se algum jogador estiver com 0 cartas
            if (verificarGanhador() != null) {
                // Avisa todo mundo que a partida terminou
                sendMessageToAllPlayers(new TypedMessage("match-ended", verificarGanhador()));
            }
        }
    }

    // Sempre que uma carta for jogada, define ela como última carta jogada   
    public void onPlayCardMessage(PlayCardMessage message) {
        this.lastPlayCardMessage = message;
    }

    // Sempre que uma carta for comprada, define ela como última carta comprada
    public void onBuyCardMessage(BuyCardMessage message) {
        this.lastBuyCardMessage = message;
    }

    // Verifica se uma carta foi jogada ou se o jogador comprou
    // Essa verificação é feita com base no atributo que guarda a última carta jogada
    // Ou pelo número de cartas do jogador
    private Callable<Boolean> doesPlayerPlay(int playerId) {
        return () -> {

            if (lastPlayCardMessage != null) { // Se a carta da mesa mudou
                return lastPlayCardMessage.getUserId() == playerId;
            } else if (lastBuyCardMessage != null) { // Se uma carta foi comprada
                return lastBuyCardMessage.getUserId() == playerId;
            }

            return false;
        };
    }

    // Recupera o próximo player que deve jogar
    private int getProximoPlayer(int posAtual) {
        // Se chegou ao último, volta ao começo da lista
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

    // Indica que uma carta foi jogada
    public void playCard(PlayCardMessage message) {
        System.out.println("O " + message.getUserId() + " jogou um " + message.getCardPlayed().getImageUrl());
        System.out.println("Ele tem " + match.getPlayers().get(message.getUserId()).getDeck().size() + " cartas.");
        this.onPlayCardMessage(message);
    }

    //Gera carta inicial virada para cima na mesa
    private void gerarCartaMesa() {
        //Pega uma carta aleatoriamente para botar na mesa
        Card carta = this.match.getMatchdeck().get(random.nextInt(this.match.getMatchdeck().size()));

        //Ela não pode ser preta
        if (carta.getColor() == Card.Color.BLACK) {
            gerarCartaMesa();
        } else {
            //Tira ela do baralho e bota na pilha de descartadas
            match.getMatchdeck().remove(carta);
            match.getDiscard().add(carta);

            //Atualiza a imagem na tela
            sendMessageToAllPlayers(new TypedMessage("first-card", carta));
        }
    }

    // Verifica se algum jogador estiver com 0 cartas
    public User verificarGanhador() {
        User winner = null;

        for (User us : match.getPlayers().values()) {
            if (us.getDeck().size() == 0) {
                winner = us;
            }   
        }

        return winner;
    }

}
