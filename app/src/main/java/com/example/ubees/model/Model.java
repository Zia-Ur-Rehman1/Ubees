package com.example.ubees.model;

public class Model {
    String ImageUrl;
    public  Model(){}
    public Model(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
