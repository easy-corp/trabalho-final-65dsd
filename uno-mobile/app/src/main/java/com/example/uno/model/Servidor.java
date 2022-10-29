package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private String IP;
    private String porta;
    private List<Jogo> jogos;

    public Servidor(String IP, String porta) {
        this.IP = IP;
        this.porta = porta;
        this.jogos = new ArrayList<>();
    }

    public String getIP() {
        return IP;
    }

    public String getPorta() {
        return porta;
    }

    public void addJogo(Jogo jogo) {
        this.jogos.add(jogo);
    }

    public List<Jogo> getJogos() {
        return this.jogos;
    }

}
