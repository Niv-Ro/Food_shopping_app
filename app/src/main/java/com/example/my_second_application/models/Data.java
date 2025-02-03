package com.example.my_second_application.models;

public class Data {

    private String name;
    private int image;
    private int id;
    private int amount;



    public Data(String name, int image,int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void updateAmountFromFirebase(int amount) {
        this.amount = amount;
    }

}
