package com.example.uxplore.model;

public class Favorites {
    private String placename;

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Favorites(String placename, String imageURL) {
        this.placename = placename;
        this.imageURL = imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String imageURL;
}
