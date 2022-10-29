package com.example.uno.model;

public class Jogador {

    private String nome;
    private int img;
    private int numCartas;

    public Jogador(String nome, int img, int numCartas) {
        this.nome = nome;
        this.img = img;
        this.numCartas = numCartas;
    }

    public String getNome() {
        return nome;
    }

    public int getImg() {
        return img;
    }

    public int getNumCartas() {
        return numCartas;
    }

    public void setNumCartas(int numCartas) {
        this.numCartas = numCartas;
    }
}
