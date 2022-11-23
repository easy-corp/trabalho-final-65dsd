package br.udesc.core.model;

import java.util.List;

public class User {
    
    private int id;                 //Id do usuario
    private String nome;            //Nome do usuario
    private String password;        //Senha do usuario
    private Avatar avatar;          //Avatar do usuario
    private List<Match> matches;    //Partidas jogadas pelo usuario
    private List<Card> deck;        //Cartas do usuario
    private boolean isUno;          //Propriedade que define se o usuario pediu uno
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public boolean isUno() {
        return isUno;
    }

    public void setIsUno(boolean opt) {
        this.isUno = opt;
    }

}