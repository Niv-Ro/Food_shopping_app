package com.example.my_second_application.models;
public class CheckoutData {
    private String name;
    private int amount;
    private int image;

    public CheckoutData() { } // Needed for Firebase

    public CheckoutData(String name, int amount, int image) {
        this.name = name;
        this.amount = amount;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getImage() {
        return image;
    }
}

