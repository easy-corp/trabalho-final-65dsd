package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;                 //Id do usuario
    private String name;            //Nome do usuario
    private String password;        //Senha do usuario
    private Avatar avatar;          //Avatar do usuario
    private List<Card> deck;        //Cartas do usuario
    private boolean isUno;          //Propriedade que define se o usuario pediu uno
    private UserStatus status;      //Status do jogador, se esta pronto para comecar

    public User(String name, String password, Avatar avatar) {
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.deck = new ArrayList<>();
        this.status = UserStatus.UNREADY;
        this.isUno = false;
    }

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

    public int getNumCartas() {
        return deck.size();
    }

    public void addCarta(Card carta) {
        this.deck.add(carta);
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public boolean isUno() {
        return isUno;
    }

    public void setIsUno(boolean opt) {
        isUno = opt;
    }

    public enum UserStatus {
        UNREADY,
        READY
    }

}
