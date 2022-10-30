package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    private String nome;
    private String senha;
    private Avatar avatar;
    private int jogos;
    private int vitorias;
    private List<Carta> deck;

    public Jogador(String nome, String senha, Avatar avatar) {
        this.nome = nome;
        this.avatar = avatar;
        this.jogos = 0;
        this.vitorias = 0;
        this.deck = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public int getNumCartas() {
        return deck.size();
    }

    public int getJogos() {
        return jogos;
    }

    public void setJogos(int jogos) {
        this.jogos = jogos;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public String getSenha() {
        return senha;
    }

    public void addCarta(Carta carta) {
        this.deck.add(carta);
    }

    public List<Carta> getDeck() {
        return this.deck;
    }

}
