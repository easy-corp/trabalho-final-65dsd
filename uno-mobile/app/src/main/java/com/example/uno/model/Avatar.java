package com.example.uno.model;

public class Avatar {

    private boolean clicado;
    private int img;
    private int imgSelecionado;
    private int imgNaoSelecionado;

    public Avatar(int imgNaoSelecionado, int imgSelecionado) {
        this.imgSelecionado = imgSelecionado;
        this.imgNaoSelecionado = imgNaoSelecionado;
        this.clicado = false;
        this.img = imgNaoSelecionado;
    }

    public boolean isClicado() {
        return clicado;
    }

    public void setClicado(boolean opt) {
        if (opt) {
            this.img = imgSelecionado;
        } else {
            this.img = imgNaoSelecionado;
        }

        this.clicado = opt;
    }

    public int getImg() {
        return img;
    }

    public int getImgSelecionado() {
        return this.imgSelecionado;
    }

    public int getImgNaoSelecionado() {
        return this.imgSelecionado;
    }

}
