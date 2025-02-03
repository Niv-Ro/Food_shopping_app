package com.example.my_second_application.models;

public class User {

     private String email;
    private String phone;
    private String name;


    public User(String email, String phone,String name) {
        this.email = email;
        this.phone = phone;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
