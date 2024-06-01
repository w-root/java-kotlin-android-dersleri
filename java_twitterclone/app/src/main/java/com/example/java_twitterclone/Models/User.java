package com.example.java_twitterclone.Models;

public class User {

    private String userId;
    private String userName;
    private String name;
    private String email;
    private String date;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User(String userId, String userName, String name, String email, String date) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.date = date;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
