package com.example.uno.model;

public class Card {

    private String simbolo;        //Simbolo da carta
    private Color color;           //Cor da carta (enum)
    private String imageUrl;       //R.drawable.avatar_X (sendo que X varia de 1 a 6)

    public Card(String simbolo, Color color, String imageUrl) {
        this.simbolo = simbolo;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public enum Color {
        RED,
        BLUE,
        BLACK,
        YELLOW,
        GREEN
    }

    //Verifica se outra carta pode ser descartada em cima dessa
    //Se a cor OU simbolo for igual
    //Se uma delas for preta pode descartar sempre
    public boolean isCompativel(Card cartaOutra) {
        if (this.getColor() == Color.BLACK || (cartaOutra.getColor() == Color.BLACK)) {
            return true;
        } else {
            if (this.getColor() == cartaOutra.getColor() ||
                    this.getSimbolo().contentEquals(cartaOutra.getSimbolo())) {
                return true;
            }
        }

        return false;
    }

}
