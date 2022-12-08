package com.example.uno.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;                 //Id do usuario
    private static int idCont = 0;
    private String name;            //Nome do usuario
    private String password;        //Senha do usuario
    private Avatar avatar;          //Avatar do usuario
    private List<Card> deck;        //Cartas do usuario
    private boolean isUno;          //Propriedade que define se o usuario pediu uno
    private UserStatus status;      //Status do jogador, se esta pronto para comecar
    private int qtdCartas;          //Qtd de cartas na mão do jogador

    public User(String name, String password, Avatar avatar) {
        this.id = ++idCont;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.deck = new ArrayList<>();
        this.status = UserStatus.UNREADY;
        this.isUno = false;
    }

    public int getUserId() {
        return id;
    }

    public void setUserId(int userId) {
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
        return this.qtdCartas;
    }

    public int popCarta(Card carta) {
        this.deck.remove(carta);
        this.setQtdCartas(this.deck.size());

        return this.deck.size();
    }

    public void setQtdCartas(int qtd) {
        this.qtdCartas = qtd;
    }

    public void descartaCarta() {
        this.qtdCartas = qtdCartas - 1;
    }

    public void compraCarta() {
        this.qtdCartas = qtdCartas + 1;
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

    public void removeCarta(Card carta) {
        this.getDeck().remove(carta);
        this.setQtdCartas(deck.size());
    }

    public enum UserStatus {
        UNREADY,
        READY
    }

}
