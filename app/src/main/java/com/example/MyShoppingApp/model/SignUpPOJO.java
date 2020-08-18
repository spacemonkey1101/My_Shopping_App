package com.example.MyShoppingApp.model;


public class SignUpPOJO {
    private int id;
    private String phone_number;
    private String name;
    private String user_id;
    private String email;

    public String getUser_id() {
        return user_id;
    }

    public SignUpPOJO(String phoneNumber, String name, String email) {
        this.phone_number = phoneNumber;
        this.name = name;
        this.email = email;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}