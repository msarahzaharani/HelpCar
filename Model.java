package com.example.helpcarapps;

public class Model {

    int image;
    String name,details;

    public Model(int image, String name, String details){

        this.image = image;
        this.name = name;
        this.details = details;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
