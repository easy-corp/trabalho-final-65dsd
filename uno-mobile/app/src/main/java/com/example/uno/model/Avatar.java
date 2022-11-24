package com.example.uno.model;

public class Avatar {

    private int id;                          //Id do avatar
    private String imageUrl;                 //Imagem do avatar
    private String imgSelecionado;           //Imagem normal do avatar
    private String imgNaoSelecionado;        //Imagem do avatar com contorno
    private boolean clicado;                 //Controle de click no avatar

    public Avatar(int id, String imageUrl) {
        this.imageUrl = imageUrl;
        this.imgSelecionado = imageUrl;
        this.imgNaoSelecionado = imageUrl + "_selecionado";
        this.clicado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isClicado() {
        return clicado;
    }

    public void setClicado(boolean opt) {
        if (opt) {
            this.imageUrl = imgSelecionado;
        } else {
            this.imageUrl = imgNaoSelecionado;
        }

        this.clicado = opt;
    }

    public String getImg() {
        return imageUrl;
    }

}
