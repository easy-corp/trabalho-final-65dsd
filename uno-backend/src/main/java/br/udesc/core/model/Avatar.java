package br.udesc.core.model;

public class Avatar {

    private int id;               //id do avatar
    private static int idCont = -1;
    private String imageUrl;      //R.drawable.avatar_X (sendo que X varia de 1 a 6)

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

    @Override
    public String toString() {
        return "Avatar [id=" + id + ", imageUrl=" + imageUrl + "]";
    }

    

}