package com.example.uno.control;

import com.example.uno.R;
import com.example.uno.model.Carta;

import java.util.ArrayList;
import java.util.List;

public class Uno {

    private static Uno instance;
    private List<Carta> baralho;

    private Uno() {
        gerarBaralho();
    }

    public synchronized static Uno getInstance() {
        if (instance == null) {
            instance = new Uno();
        }

        return instance;
    }

    public List<Carta> getBaralho() {
        return this.baralho;
    }

    private void gerarBaralho() {
        this.baralho = new ArrayList<>();

        //Numeros
        for (int i = 0; i < 2; i++) {
            //1
            this.baralho.add(new Carta("1", "blue", R.drawable.blue_1));
            this.baralho.add(new Carta("1", "green", R.drawable.green_1));
            this.baralho.add(new Carta("1", "red", R.drawable.red_1));
            this.baralho.add(new Carta("1", "yellow", R.drawable.yellow_1));

            //2
            this.baralho.add(new Carta("2", "blue", R.drawable.blue_2));
            this.baralho.add(new Carta("2", "green", R.drawable.green_2));
            this.baralho.add(new Carta("2", "red", R.drawable.red_2));
            this.baralho.add(new Carta("2", "yellow", R.drawable.yellow_2));

            //3
            this.baralho.add(new Carta("3", "blue", R.drawable.blue_3));
            this.baralho.add(new Carta("3", "green", R.drawable.green_3));
            this.baralho.add(new Carta("3", "red", R.drawable.red_3));
            this.baralho.add(new Carta("3", "yellow", R.drawable.yellow_3));

            //4
            this.baralho.add(new Carta("4", "blue", R.drawable.blue_4));
            this.baralho.add(new Carta("4", "green", R.drawable.green_4));
            this.baralho.add(new Carta("4", "red", R.drawable.red_4));
            this.baralho.add(new Carta("4", "yellow", R.drawable.yellow_4));

            //5
            this.baralho.add(new Carta("5", "blue", R.drawable.blue_5));
            this.baralho.add(new Carta("5", "green", R.drawable.green_5));
            this.baralho.add(new Carta("5", "red", R.drawable.red_5));
            this.baralho.add(new Carta("5", "yellow", R.drawable.yellow_5));

            //6
            this.baralho.add(new Carta("6", "blue", R.drawable.blue_6));
            this.baralho.add(new Carta("6", "green", R.drawable.green_6));
            this.baralho.add(new Carta("6", "red", R.drawable.red_6));
            this.baralho.add(new Carta("6", "yellow", R.drawable.yellow_6));

            //7
            this.baralho.add(new Carta("7", "blue", R.drawable.blue_7));
            this.baralho.add(new Carta("7", "green", R.drawable.green_7));
            this.baralho.add(new Carta("7", "red", R.drawable.red_7));
            this.baralho.add(new Carta("7", "yellow", R.drawable.yellow_7));

            //8
            this.baralho.add(new Carta("8", "blue", R.drawable.blue_8));
            this.baralho.add(new Carta("8", "green", R.drawable.green_8));
            this.baralho.add(new Carta("8", "red", R.drawable.red_8));
            this.baralho.add(new Carta("8", "yellow", R.drawable.yellow_8));

            //9
            this.baralho.add(new Carta("9", "blue", R.drawable.blue_9));
            this.baralho.add(new Carta("9", "green", R.drawable.green_9));
            this.baralho.add(new Carta("9", "red", R.drawable.red_9));
            this.baralho.add(new Carta("9", "yellow", R.drawable.yellow_9));
        }

        //Zeros
        this.baralho.add(new Carta("0", "blue", R.drawable.blue_0));
        this.baralho.add(new Carta("0", "green", R.drawable.green_0));
        this.baralho.add(new Carta("0", "red", R.drawable.red_0));
        this.baralho.add(new Carta("0", "yellow", R.drawable.yellow_0));

        //Especiais
        for (int i = 0; i < 2; i++) {
            //Block
            this.baralho.add(new Carta("block", "blue", R.drawable.blue_block));
            this.baralho.add(new Carta("block", "green", R.drawable.green_block));
            this.baralho.add(new Carta("block", "red", R.drawable.red_block));
            this.baralho.add(new Carta("block", "yellow", R.drawable.yellow_block));

            //Reverse
            this.baralho.add(new Carta("reverse", "blue", R.drawable.blue_reverse));
            this.baralho.add(new Carta("reverse", "green", R.drawable.green_reverse));
            this.baralho.add(new Carta("reverse", "red", R.drawable.red_reverse));
            this.baralho.add(new Carta("reverse", "yellow", R.drawable.yellow_reverse));

            //+2
            this.baralho.add(new Carta("+2", "blue", R.drawable.blue_plus2));
            this.baralho.add(new Carta("+2", "green", R.drawable.green_plus2));
            this.baralho.add(new Carta("+2", "red", R.drawable.red_plus2));
            this.baralho.add(new Carta("+2", "yellow", R.drawable.yellow_plus2));
        }

        //Coringas
        for (int i = 0; i < 4;i++) {
            this.baralho.add(new Carta("+4", "black", R.drawable.black_plus4));
            this.baralho.add(new Carta("color_choose", "black", R.drawable.black_color_choose));
        }

    }

}
