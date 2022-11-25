package com.example.uno.model;

public class Avatar {

    private int id;                          //Id do avatar
    private static int idCont = 0;
    private String imageUrl;                 //Imagem do avatar

    public Avatar(String imageUrl) {
        this.id = ++idCont;
        this.imageUrl = imageUrl;
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

    public void click(boolean opt) {
        if (opt) {
            this.imageUrl = this.imageUrl + "_selecionado";
        } else {
            if (this.imageUrl.indexOf("_selecionado") > 0) {
                this.imageUrl = this.imageUrl.substring(0, this.imageUrl.indexOf("_selecionado"));
            }
        }
    }

}
