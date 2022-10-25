package com.example.uno.model;

public class Avatar {

    private boolean clicado;
    private int imgSelecionado;
    private int imgNaoSelecionado;

    public Avatar(int imgNaoSelecionado, int imgSelecionado) {
        this.imgSelecionado = imgSelecionado;
        this.imgNaoSelecionado = imgNaoSelecionado;
        this.clicado = false;
    }

    public boolean isClicado() {
        return clicado;
    }

    public void setClicado(boolean clicado) {
        this.clicado = clicado;
    }

    public int getImgSelecionado() {
        return imgSelecionado;
    }

    public void setImgSelecionado(int imgSelecionado) {
        this.imgSelecionado = imgSelecionado;
    }

    public int getImgNaoSelecionado() {
        return imgNaoSelecionado;
    }

    public void setImgNaoSelecionado(int imgNaoSelecionado) {
        this.imgNaoSelecionado = imgNaoSelecionado;
    }
}
