package com.example.uno.model;

import com.example.uno.R;

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
        this.deck = gerarBaralho();
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
    public boolean isEntravel() {
        if (this.players.size() < this.qtdPlayers) {
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

    public Stack<Card> getDiscard() {
        return this.discard;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    private List<Card> gerarBaralho() {
        List<Card> baralho = new ArrayList<>();

        //Numeros
        for (int i = 0; i < 2; i++) {
            //1
            baralho.add(new Card("1", Card.Color.BLUE, R.drawable.blue_1));
            baralho.add(new Card("1", Card.Color.GREEN, R.drawable.green_1));
            baralho.add(new Card("1", "red", R.drawable.red_1));
            baralho.add(new Card("1", "yellow", R.drawable.yellow_1));

            //2
            baralho.add(new Card("2", Card.Color.BLUE, R.drawable.blue_2));
            baralho.add(new Card("2", Card.Color.GREEN, R.drawable.green_2));
            baralho.add(new Card("2", "red", R.drawable.red_2));
            baralho.add(new Card("2", "yellow", R.drawable.yellow_2));

            //3
            baralho.add(new Card("3", Card.Color.BLUE, R.drawable.blue_3));
            baralho.add(new Card("3", Card.Color.GREEN, R.drawable.green_3));
            baralho.add(new Card("3", "red", R.drawable.red_3));
            baralho.add(new Card("3", "yellow", R.drawable.yellow_3));

            //4
            baralho.add(new Card("4", Card.Color.BLUE, R.drawable.blue_4));
            baralho.add(new Card("4", Card.Color.GREEN, R.drawable.green_4));
            baralho.add(new Card("4", "red", R.drawable.red_4));
            baralho.add(new Card("4", "yellow", R.drawable.yellow_4));

            //5
            baralho.add(new Card("5", Card.Color.BLUE, R.drawable.blue_5));
            baralho.add(new Card("5", Card.Color.GREEN, R.drawable.green_5));
            baralho.add(new Card("5", "red", R.drawable.red_5));
            baralho.add(new Card("5", "yellow", R.drawable.yellow_5));

            //6
            baralho.add(new Card("6", Card.Color.BLUE, R.drawable.blue_6));
            baralho.add(new Card("6", Card.Color.GREEN, R.drawable.green_6));
            baralho.add(new Card("6", "red", R.drawable.red_6));
            baralho.add(new Card("6", "yellow", R.drawable.yellow_6));

            //7
            baralho.add(new Card("7", Card.Color.BLUE, R.drawable.blue_7));
            baralho.add(new Card("7", Card.Color.GREEN, R.drawable.green_7));
            baralho.add(new Card("7", "red", R.drawable.red_7));
            baralho.add(new Card("7", "yellow", R.drawable.yellow_7));

            //8
            baralho.add(new Card("8", Card.Color.BLUE, R.drawable.blue_8));
            baralho.add(new Card("8", Card.Color.GREEN, R.drawable.green_8));
            baralho.add(new Card("8", "red", R.drawable.red_8));
            baralho.add(new Card("8", "yellow", R.drawable.yellow_8));

            //9
            baralho.add(new Card("9", Card.Color.BLUE, R.drawable.blue_9));
            baralho.add(new Card("9", Card.Color.GREEN, R.drawable.green_9));
            baralho.add(new Card("9", "red", R.drawable.red_9));
            baralho.add(new Card("9", "yellow", R.drawable.yellow_9));
        }

        //Zeros
        baralho.add(new Card("0", Card.Color.BLUE, R.drawable.blue_0));
        baralho.add(new Card("0", Card.Color.GREEN, R.drawable.green_0));
        baralho.add(new Card("0", "red", R.drawable.red_0));
        baralho.add(new Card("0", "yellow", R.drawable.yellow_0));

        //Especiais
        for (int i = 0; i < 2; i++) {
            //Block
            baralho.add(new Card("block", Card.Color.BLUE, R.drawable.blue_block));
            baralho.add(new Card("block", Card.Color.GREEN, R.drawable.green_block));
            baralho.add(new Card("block", "red", R.drawable.red_block));
            baralho.add(new Card("block", "yellow", R.drawable.yellow_block));

            //Reverse
            baralho.add(new Card("reverse", Card.Color.BLUE, R.drawable.blue_reverse));
            baralho.add(new Card("reverse", Card.Color.GREEN, R.drawable.green_reverse));
            baralho.add(new Card("reverse", "red", R.drawable.red_reverse));
            baralho.add(new Card("reverse", "yellow", R.drawable.yellow_reverse));

            //+2
            baralho.add(new Card("+2", Card.Color.BLUE, R.drawable.blue_plus2));
            baralho.add(new Card("+2", Card.Color.GREEN, R.drawable.green_plus2));
            baralho.add(new Card("+2", "red", R.drawable.red_plus2));
            baralho.add(new Card("+2", "yellow", R.drawable.yellow_plus2));
        }

        //Coringas
        for (int i = 0; i < 4; i++) {
            baralho.add(new Card("+4", Card.Color.BLACK, R.drawable.black_plus4));
            baralho.add(new Card("color_choose", Card.Color.BLACK, R.drawable.black_color_choose));
        }

        return baralho;
    }

    public enum MatchStatus {

        WAITING,
        PLAYING

    }

}
