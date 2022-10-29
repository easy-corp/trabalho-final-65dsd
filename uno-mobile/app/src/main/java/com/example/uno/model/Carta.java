package com.example.uno.model;

public class Carta {

    private String simbolo;
    private String cor;
    private int img;

    public Carta(String simbolo, String cor, int img) {
        this.simbolo = simbolo;
        this.cor = cor;
        this.img = img;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getCor() {
        return cor;
    }

    public int getImg() {
        return img;
    }
}
