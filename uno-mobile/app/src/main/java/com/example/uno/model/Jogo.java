package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class Jogo {

    private String nome;
    private int partCapacidade;
    private int partAtual;
    private List<Jogador> jogadores;

    public Jogo(String nome, int partCapacidade, int partAtual) {
        this.nome = nome;
        this.partCapacidade = partCapacidade;
        this.partAtual = partAtual;
        this.jogadores = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPartCapacidade() {
        return partCapacidade;
    }

    public void setPartCapacidade(int partCapacidade) {
        this.partCapacidade = partCapacidade;
    }

    public int getPartAtual() {
        return partAtual;
    }

    public void setPartAtual(int partAtual) {
        this.partAtual = partAtual;
    }

    public boolean isEntravel() {
        if (this.partAtual < this.partCapacidade) {
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
}
