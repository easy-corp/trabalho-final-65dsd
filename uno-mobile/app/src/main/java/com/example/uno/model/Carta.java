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

    //Verifica se outra carta pode ser descartada em cima dessa
    //Se a cor OU simbolo for igual
    //Se uma delas for preta pode descartar sempre
    public boolean isCompativel(Carta cartaOutra) {
        if (this.getCor().contentEquals("black") || cartaOutra.getCor().contentEquals("black")) {
            return true;
        } else {
            if (this.getCor().contentEquals(cartaOutra.getCor()) ||
                    this.getSimbolo().contentEquals(cartaOutra.getSimbolo())) {
                return true;
            }
        }

        return false;
    }

}
