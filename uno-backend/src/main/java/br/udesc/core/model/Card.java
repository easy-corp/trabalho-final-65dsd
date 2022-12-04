package br.udesc.core.model;

public class Card {

    private String simbolo;       //Simbolo da carta
    private Color color;          //Cor da carta (enum)
    private String imageUrl;      //R.drawable.avatar_X (sendo que X varia de 1 a 6)

    public Card(String simbolo, Color color, String imageUrl) {
        this.simbolo = simbolo;
        this.color = color;
        this.imageUrl = imageUrl;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public enum Color {
        RED,
        BLUE,
        BLACK,
        YELLOW,
        GREEN
    }

}