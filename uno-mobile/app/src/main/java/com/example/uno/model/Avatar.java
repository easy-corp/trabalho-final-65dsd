package com.example.uno.model;

public class Avatar {

    private boolean clicado;
    private int img;
    private int imgSelecionado;
    private int imgNaoSelecionado;

    public Avatar(int imgNaoSelecionado, int imgSelecionado) {
        this.img = imgNaoSelecionado;                                //Imagem do avatar
        this.imgSelecionado = imgSelecionado;                        //Imagem normal do avatar
        this.imgNaoSelecionado = imgNaoSelecionado;                  //Imagem do avatar com contorno
        this.clicado = false;                                        //Controle de click no avatar
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

}
