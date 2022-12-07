package com.example.uno.model;

import com.example.uno.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Match {

    private int matchId;                    //id da partida
    private static int idCont = 0;
    private String name;                    //Nome do jogo
    private int qtdPlayers;                 //Capacidade máxima de jogadores
    private MatchStatus status;             //Status da partida
    private Map<Integer, User> players;     //Lista de jogadores
    private List<Card> deck;                //Todas as cartas do baralho
    private Stack<Card> discard;            //As cartas descartadas na mesa

    public Match(String name, int qtdPlayers) {
        this.matchId = ++idCont;
        this.name = name;
        this.qtdPlayers = qtdPlayers;
        this.status = MatchStatus.WAITING;
        this.players = new HashMap<>();
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

    //Verifica a possibilidade de entrar na sala
    //Se ainda tiver vagas e a sala estiver no status de WAITING
    public boolean isEntravel() {
        if (this.players.size() < this.qtdPlayers && this.status == MatchStatus.WAITING) {
            return true;
        } else {
            return false;
        }
    }

    //Verifica a possibilidade de dropar a carta na mesa
    public boolean isDropavel(Card carta) {
        //Se for preta pode descartar sempre
        if (carta.isCompativel(this.discard.peek())) {
            return true;
        }

        return false;
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
