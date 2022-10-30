package com.example.uno.model;

import com.example.uno.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Jogo {

    private String nome;                     //Nome do jogo
    private int partCapacidade;              //Capacidade máxima de jogadores
    private List<Jogador> jogadores;         //Lista de jogadores
    private List<Carta> baralho;             //Todas as cartas do baralho
    private Stack<Carta> descartadas;        //As cartas descartadas na mesa

    public Jogo(String nome, int partCapacidade) {
        this.nome = nome;
        this.partCapacidade = partCapacidade;
        this.jogadores = new ArrayList<>();
        this.baralho = gerarBaralho();
        this.descartadas = new Stack<>();
    }

    public String getNome() {
        return nome;
    }

    public int getPartCapacidade() {
        return partCapacidade;
    }

    public boolean isEntravel() {
        if (this.jogadores.size() < this.partCapacidade) {
            return true;
        } else {
            return false;
        }
    }

    public void addJogador(Jogador jogador) {
        this.jogadores.add(jogador);
    }

    public List<Jogador> getJogadores() {
        return this.jogadores;
    }

    public List<Carta> getBaralho() {
        return this.baralho;
    }

    public Stack<Carta> getDescartadas() {
        return this.descartadas;
    }

    private List<Carta> gerarBaralho() {
        List<Carta> baralho = new ArrayList<>();

        //Numeros
        for (int i = 0; i < 2; i++) {
            //1
            baralho.add(new Carta("1", "blue", R.drawable.blue_1));
            baralho.add(new Carta("1", "green", R.drawable.green_1));
            baralho.add(new Carta("1", "red", R.drawable.red_1));
            baralho.add(new Carta("1", "yellow", R.drawable.yellow_1));

            //2
            baralho.add(new Carta("2", "blue", R.drawable.blue_2));
            baralho.add(new Carta("2", "green", R.drawable.green_2));
            baralho.add(new Carta("2", "red", R.drawable.red_2));
            baralho.add(new Carta("2", "yellow", R.drawable.yellow_2));

            //3
            baralho.add(new Carta("3", "blue", R.drawable.blue_3));
            baralho.add(new Carta("3", "green", R.drawable.green_3));
            baralho.add(new Carta("3", "red", R.drawable.red_3));
            baralho.add(new Carta("3", "yellow", R.drawable.yellow_3));

            //4
            baralho.add(new Carta("4", "blue", R.drawable.blue_4));
            baralho.add(new Carta("4", "green", R.drawable.green_4));
            baralho.add(new Carta("4", "red", R.drawable.red_4));
            baralho.add(new Carta("4", "yellow", R.drawable.yellow_4));

            //5
            baralho.add(new Carta("5", "blue", R.drawable.blue_5));
            baralho.add(new Carta("5", "green", R.drawable.green_5));
            baralho.add(new Carta("5", "red", R.drawable.red_5));
            baralho.add(new Carta("5", "yellow", R.drawable.yellow_5));

            //6
            baralho.add(new Carta("6", "blue", R.drawable.blue_6));
            baralho.add(new Carta("6", "green", R.drawable.green_6));
            baralho.add(new Carta("6", "red", R.drawable.red_6));
            baralho.add(new Carta("6", "yellow", R.drawable.yellow_6));

            //7
            baralho.add(new Carta("7", "blue", R.drawable.blue_7));
            baralho.add(new Carta("7", "green", R.drawable.green_7));
            baralho.add(new Carta("7", "red", R.drawable.red_7));
            baralho.add(new Carta("7", "yellow", R.drawable.yellow_7));

            //8
            baralho.add(new Carta("8", "blue", R.drawable.blue_8));
            baralho.add(new Carta("8", "green", R.drawable.green_8));
            baralho.add(new Carta("8", "red", R.drawable.red_8));
            baralho.add(new Carta("8", "yellow", R.drawable.yellow_8));

            //9
            baralho.add(new Carta("9", "blue", R.drawable.blue_9));
            baralho.add(new Carta("9", "green", R.drawable.green_9));
            baralho.add(new Carta("9", "red", R.drawable.red_9));
            baralho.add(new Carta("9", "yellow", R.drawable.yellow_9));
        }

        //Zeros
        baralho.add(new Carta("0", "blue", R.drawable.blue_0));
        baralho.add(new Carta("0", "green", R.drawable.green_0));
        baralho.add(new Carta("0", "red", R.drawable.red_0));
        baralho.add(new Carta("0", "yellow", R.drawable.yellow_0));

        //Especiais
        for (int i = 0; i < 2; i++) {
            //Block
            baralho.add(new Carta("block", "blue", R.drawable.blue_block));
            baralho.add(new Carta("block", "green", R.drawable.green_block));
            baralho.add(new Carta("block", "red", R.drawable.red_block));
            baralho.add(new Carta("block", "yellow", R.drawable.yellow_block));

            //Reverse
            baralho.add(new Carta("reverse", "blue", R.drawable.blue_reverse));
            baralho.add(new Carta("reverse", "green", R.drawable.green_reverse));
            baralho.add(new Carta("reverse", "red", R.drawable.red_reverse));
            baralho.add(new Carta("reverse", "yellow", R.drawable.yellow_reverse));

            //+2
            baralho.add(new Carta("+2", "blue", R.drawable.blue_plus2));
            baralho.add(new Carta("+2", "green", R.drawable.green_plus2));
            baralho.add(new Carta("+2", "red", R.drawable.red_plus2));
            baralho.add(new Carta("+2", "yellow", R.drawable.yellow_plus2));
        }

        //Coringas
        for (int i = 0; i < 4; i++) {
            baralho.add(new Carta("+4", "black", R.drawable.black_plus4));
            baralho.add(new Carta("color_choose", "black", R.drawable.black_color_choose));
        }

        return baralho;
    }

}
