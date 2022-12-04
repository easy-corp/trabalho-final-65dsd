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
        this.players.remove(player);
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

//    private List<Card> gerarBaralho() {
//        List<Card> baralho = new ArrayList<>();
//
//        //Numeros
//        for (int i = 0; i < 2; i++) {
//            //1
//            baralho.add(new Card("1", Card.Color.BLUE, "blue_1"));
//            baralho.add(new Card("1", Card.Color.GREEN, "green_1"));
//            baralho.add(new Card("1", Card.Color.RED, "red_1"));
//            baralho.add(new Card("1", Card.Color.YELLOW, "yellow_1"));
//
//            //2
//            baralho.add(new Card("2", Card.Color.BLUE, "blue_2"));
//            baralho.add(new Card("2", Card.Color.GREEN, "green_2"));
//            baralho.add(new Card("2", Card.Color.RED, "red_2"));
//            baralho.add(new Card("2", Card.Color.YELLOW, "yellow_2"));
//
//            //3
//            baralho.add(new Card("3", Card.Color.BLUE, "blue_3"));
//            baralho.add(new Card("3", Card.Color.GREEN, "green_3"));
//            baralho.add(new Card("3", Card.Color.RED, "red_3"));
//            baralho.add(new Card("3", Card.Color.YELLOW, "yellow_3"));
//
//            //4
//            baralho.add(new Card("4", Card.Color.BLUE, "blue_4"));
//            baralho.add(new Card("4", Card.Color.GREEN, "green_4"));
//            baralho.add(new Card("4", Card.Color.RED, "red_4"));
//            baralho.add(new Card("4", Card.Color.YELLOW, "yellow_4"));
//
//            //5
//            baralho.add(new Card("5", Card.Color.BLUE, "blue_5"));
//            baralho.add(new Card("5", Card.Color.GREEN, "green_5"));
//            baralho.add(new Card("5", Card.Color.RED, "red_5"));
//            baralho.add(new Card("5", Card.Color.YELLOW, "yellow_5"));
//
//            //6
//            baralho.add(new Card("6", Card.Color.BLUE, "blue_6"));
//            baralho.add(new Card("6", Card.Color.GREEN, "green_6"));
//            baralho.add(new Card("6", Card.Color.RED, "red_6"));
//            baralho.add(new Card("6", Card.Color.YELLOW, "yellow_6"));
//
//            //7
//            baralho.add(new Card("7", Card.Color.BLUE, "blue_7"));
//            baralho.add(new Card("7", Card.Color.GREEN, "green_7"));
//            baralho.add(new Card("7", Card.Color.RED, "red_7"));
//            baralho.add(new Card("7", Card.Color.YELLOW, "yellow_7"));
//
//            //8
//            baralho.add(new Card("8", Card.Color.BLUE, "blue_8"));
//            baralho.add(new Card("8", Card.Color.GREEN, "green_8"));
//            baralho.add(new Card("8", Card.Color.RED, "red_8"));
//            baralho.add(new Card("8", Card.Color.YELLOW, "yellow_8"));
//
//            //9
//            baralho.add(new Card("9", Card.Color.BLUE, "blue_9"));
//            baralho.add(new Card("9", Card.Color.GREEN, "green_9"));
//            baralho.add(new Card("9", Card.Color.RED, "red_9"));
//            baralho.add(new Card("9", Card.Color.YELLOW, "yellow_9"));
//        }
//
//        //Zeros
//        baralho.add(new Card("0", Card.Color.BLUE, "blue_0"));
//        baralho.add(new Card("0", Card.Color.GREEN, "green_0"));
//        baralho.add(new Card("0", Card.Color.RED, "red_0"));
//        baralho.add(new Card("0", Card.Color.YELLOW, "yellow_0"));
//
//        //Especiais
//        for (int i = 0; i < 2; i++) {
//            //Block
//            baralho.add(new Card("block", Card.Color.BLUE, "blue_block"));
//            baralho.add(new Card("block", Card.Color.GREEN, "green_block"));
//            baralho.add(new Card("block", Card.Color.RED, "red_block"));
//            baralho.add(new Card("block", Card.Color.YELLOW, "yellow_block"));
//
//            //Reverse
//            baralho.add(new Card("reverse", Card.Color.BLUE, "blue_reverse"));
//            baralho.add(new Card("reverse", Card.Color.GREEN, "green_reverse"));
//            baralho.add(new Card("reverse", Card.Color.RED, "red_reverse"));
//            baralho.add(new Card("reverse", Card.Color.YELLOW, "yellow_reverse"));
//
//            //+2
//            baralho.add(new Card("+2", Card.Color.BLUE, "blue_plus2"));
//            baralho.add(new Card("+2", Card.Color.GREEN, "green_plus2"));
//            baralho.add(new Card("+2", Card.Color.RED, "red_plus2"));
//            baralho.add(new Card("+2", Card.Color.YELLOW, "yellow_plus2"));
//        }
//
//        //Coringas
//        for (int i = 0; i < 4; i++) {
//            baralho.add(new Card("+4", Card.Color.BLACK, "black_plus4"));
//            baralho.add(new Card("color_choose", Card.Color.BLACK, "black_color_choose"));
//        }
//
//        return baralho;
//    }

    public enum MatchStatus {

        WAITING,
        PLAYING

    }

}
