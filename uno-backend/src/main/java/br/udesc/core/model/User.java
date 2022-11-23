package br.udesc.core.model;

import java.util.List;

public class User {
    
    private int id;                 //Id do usuario
    private String name;            //Nome do usuario
    private String password;        //Senha do usuario
    private Avatar avatar;          //Avatar do usuario
    private List<Card> deck;        //Cartas do usuario
    private boolean isUno;          //Propriedade que define se o usuario pediu uno
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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