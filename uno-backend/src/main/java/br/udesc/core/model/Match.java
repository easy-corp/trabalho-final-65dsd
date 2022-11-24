package br.udesc.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Match {

    private int matchId;                    //id da partida
    private static int idCont = 0;
    private String name;                    //Nome do jogo
    private int qtdPlayers;                 //Capacidade máxima de jogadores
    private MatchStatus status;             //Status da partida
    private List<User> players;             //Lista de jogadores
    private List<Card> deck;                //Todas as cartas do baralho
    private Stack<Card> discard;            //As cartas descartadas na mesa

    public Match(String name, int qtdPlayers) {
        this.matchId = ++idCont;
        this.name = name;
        this.qtdPlayers = qtdPlayers;
        this.status = MatchStatus.WAITING;
        this.players = new ArrayList<>();
        this.deck = new ArrayList<>();
        this.discard = new Stack<>();
    }

    public int getMatchId() {
        return matchId;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getQtdPlayers() {
        return qtdPlayers;
    }

    public void setQtdPlayers(int qtdPlayers) {
        this.qtdPlayers = qtdPlayers;
    }

    public void addPlayer(User player) {
        this.players.add(player);
    }

    public void removePlayer(User player) {
        this.players.remove(player);
    }

    public List<User> getPlayers(User player) {
        return players;
    }

    public List<Card> getDeck() {
        return this.deck;
    }



    public enum MatchStatus {

        WAITING,
        PLAYING

    }

}