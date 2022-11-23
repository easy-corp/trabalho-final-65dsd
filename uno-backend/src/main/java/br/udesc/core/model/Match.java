package br.udesc.core.model;

import java.util.List;
import java.util.Stack;

public class Match {

    private int matchId;                    //id da partida
    private String nome;                    //Nome do jogo
    private int qtdPlayers;                 //Capacidade máxima de jogadores
    private MatchStatus status;             //Status da partida
    private List<User> players;             //Lista de jogadores
    private List<Card> deck;                //Todas as cartas do baralho
    private Stack<Card> discard;            //As cartas descartadas na mesa

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
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

    public List<User> getPlayers(User player) {
        return players;
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public Stack<Card> getDiscard() {
        return this.discard;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public enum MatchStatus {

        WAITING,
        PLAYING

    }

}