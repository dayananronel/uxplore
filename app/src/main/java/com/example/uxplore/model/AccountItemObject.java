package com.example.uxplore.model;

public class AccountItemObject {

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int ImageId;
    private String title;

    public AccountItemObject(String title,int imageId) {
        ImageId = imageId;
        this.title = title;
    }



}
