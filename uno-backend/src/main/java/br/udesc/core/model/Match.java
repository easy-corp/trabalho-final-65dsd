package br.udesc.core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import br.udesc.core.model.User.UserStatus;

public class Match {

    private int matchId;                    //id da partida
    private static int idCont = -1;
    private String matchName;                //Nome do jogo
    private int qtdPlayers;                 //Capacidade máxima de jogadores
    private MatchStatus status;             //Status da partida
    private Map<Integer, User> players;     //Lista de jogadores
    private List<Card> matchdeck;           //Todas as cartas do baralho
    private Stack<Card> discard;            //As cartas descartadas na mesa
    private Random random;

    public Match(String name, int qtdPlayers) {
        this.matchId = ++idCont;
        this.matchName = name;
        this.qtdPlayers = qtdPlayers;
        this.status = MatchStatus.WAITING;
        this.players = new HashMap<Integer, User>();
        this.matchdeck = gerarBaralho();
        this.discard = new Stack<>();
        this.random = new Random();
    }

    public int getMatchId() {
        return matchId;
    }

    public String getMatchName() {
        return matchName;
    }
    
    public void setMatchName(String name) {
        this.matchName = name;
    }

    public int getQtdPlayers() {
        return qtdPlayers;
    }

    public void setQtdPlayers(int qtdPlayers) {
        this.qtdPlayers = qtdPlayers;
    }

    public void addPlayer(User player) {
        this.players.put(player.getId(), player);
    }

    public void removePlayer(User player) {
        this.players.remove(player.getId());
    }

    public Map<Integer, User> getPlayers() {
        return players;
    }

    public List<Card> getMatchdeck() {
        return this.matchdeck;
    }

    public void addDiscard(Card card) {
        this.discard.add(card);
    }

    public List<Card> getDiscard() {
        return this.discard;
    }

    public void cleanDiscard() {
        this.discard.removeAllElements();
    }

    public enum MatchStatus {

        WAITING,
        PLAYING

    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public MatchStatus getStatus() {
        return this.status;
    }

    private List<Card> gerarBaralho() {
        List<Card> baralho = new ArrayList<>();

        // Numeros
        for (int i = 0; i < 2; i++) {
            // 1
            baralho.add(new Card("1", Card.Color.BLUE, "blue_1"));
            baralho.add(new Card("1", Card.Color.GREEN, "green_1"));
            baralho.add(new Card("1", Card.Color.RED, "red_1"));
            baralho.add(new Card("1", Card.Color.YELLOW, "yellow_1"));

            // 2
            baralho.add(new Card("2", Card.Color.BLUE, "blue_2"));
            baralho.add(new Card("2", Card.Color.GREEN, "green_2"));
            baralho.add(new Card("2", Card.Color.RED, "red_2"));
            baralho.add(new Card("2", Card.Color.YELLOW, "yellow_2"));

            // 3
            baralho.add(new Card("3", Card.Color.BLUE, "blue_3"));
            baralho.add(new Card("3", Card.Color.GREEN, "green_3"));
            baralho.add(new Card("3", Card.Color.RED, "red_3"));
            baralho.add(new Card("3", Card.Color.YELLOW, "yellow_3"));

            // 4
            baralho.add(new Card("4", Card.Color.BLUE, "blue_4"));
            baralho.add(new Card("4", Card.Color.GREEN, "green_4"));
            baralho.add(new Card("4", Card.Color.RED, "red_4"));
            baralho.add(new Card("4", Card.Color.YELLOW, "yellow_4"));

            // 5
            baralho.add(new Card("5", Card.Color.BLUE, "blue_5"));
            baralho.add(new Card("5", Card.Color.GREEN, "green_5"));
            baralho.add(new Card("5", Card.Color.RED, "red_5"));
            baralho.add(new Card("5", Card.Color.YELLOW, "yellow_5"));

            // 6
            baralho.add(new Card("6", Card.Color.BLUE, "blue_6"));
            baralho.add(new Card("6", Card.Color.GREEN, "green_6"));
            baralho.add(new Card("6", Card.Color.RED, "red_6"));
            baralho.add(new Card("6", Card.Color.YELLOW, "yellow_6"));

            // 7
            baralho.add(new Card("7", Card.Color.BLUE, "blue_7"));
            baralho.add(new Card("7", Card.Color.GREEN, "green_7"));
            baralho.add(new Card("7", Card.Color.RED, "red_7"));
            baralho.add(new Card("7", Card.Color.YELLOW, "yellow_7"));

            // 8
            baralho.add(new Card("8", Card.Color.BLUE, "blue_8"));
            baralho.add(new Card("8", Card.Color.GREEN, "green_8"));
            baralho.add(new Card("8", Card.Color.RED, "red_8"));
            baralho.add(new Card("8", Card.Color.YELLOW, "yellow_8"));

            // 9
            baralho.add(new Card("9", Card.Color.BLUE, "blue_9"));
            baralho.add(new Card("9", Card.Color.GREEN, "green_9"));
            baralho.add(new Card("9", Card.Color.RED, "red_9"));
            baralho.add(new Card("9", Card.Color.YELLOW, "yellow_9"));
        }

        // Zeros
        baralho.add(new Card("0", Card.Color.BLUE, "blue_0"));
        baralho.add(new Card("0", Card.Color.GREEN, "green_0"));
        baralho.add(new Card("0", Card.Color.RED, "red_0"));
        baralho.add(new Card("0", Card.Color.YELLOW, "yellow_0"));

        // Especiais
        for (int i = 0; i < 2; i++) {
            // Block
            baralho.add(new Card("block", Card.Color.BLUE, "blue_block"));
            baralho.add(new Card("block", Card.Color.GREEN, "green_block"));
            baralho.add(new Card("block", Card.Color.RED, "red_block"));
            baralho.add(new Card("block", Card.Color.YELLOW, "yellow_block"));

            // Reverse
            baralho.add(new Card("reverse", Card.Color.BLUE, "blue_reverse"));
            baralho.add(new Card("reverse", Card.Color.GREEN, "green_reverse"));
            baralho.add(new Card("reverse", Card.Color.RED, "red_reverse"));
            baralho.add(new Card("reverse", Card.Color.YELLOW, "yellow_reverse"));

            // +2
            baralho.add(new Card("+2", Card.Color.BLUE, "blue_plus2"));
            baralho.add(new Card("+2", Card.Color.GREEN, "green_plus2"));
            baralho.add(new Card("+2", Card.Color.RED, "red_plus2"));
            baralho.add(new Card("+2", Card.Color.YELLOW, "yellow_plus2"));
        }

        // Coringas
        // for (int i = 0; i < 4; i++) {
        //     baralho.add(new Card("+4", Card.Color.BLACK, "black_plus4"));
        //     baralho.add(new Card("color_choose", Card.Color.BLACK, "black_color_choose"));
        // }

        return baralho;
    }

    //Distribui as cartas aleatoriamente entre os jogadores
    public void distribuirCartas() {
        for (User j : this.getPlayers().values()) {
            for (int i = 0; i < 7; i++) {
                //Tira uma carta do baralho para meu deck
                Card carta = this.getMatchdeck().get(this.random.nextInt(getMatchdeck().size()));
                this.getMatchdeck().remove(carta);

                j.addCarta(carta);
            }
        }
    }

    public void iniciarPartida() throws InterruptedException {
        //Identifica que a partida começou
        this.setStatus(MatchStatus.PLAYING);

        //Distribui as cartas entre os jogadores
        distribuirCartas();
    }

    public boolean allPlayersReady() {
        //Se algum ainda não estiver pronto, retorna falso
        for (User u : this.players.values()) {
            if (u.getStatus() == UserStatus.UNREADY) {
                return false;
            }
        }

        return true;
    }

    public void removerCarta(Card c) {
        for(Card card : this.matchdeck){
            if (card.getImageUrl().contentEquals(c.getImageUrl())) {
                this.matchdeck.remove(card);

                break;
            }
        }
    }

    //Pega as cartas do monte de descarte, embaralha e devolve para o deck
    public void reporCartas() {
        //Separa a carta da mesa
        Card c = this.discard.pop();

        //Embaralha o restante
        Collections.shuffle(discard);

        //Adiciona as embaralhadas ao jogo e tira da pilha do descarte
        this.matchdeck.addAll(this.discard);
        this.discard.clear();

        //Devolve a carta da mesa;
        this.discard.add(c);
    }

}