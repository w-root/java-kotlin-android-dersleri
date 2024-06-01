package com.example.java_historybookapp.models;

public class User {

    private String UserName;
    private String Email;
    private String Password;
    private String Role;

    public User(String userName, String email, String password, String role) {
        UserName = userName;
        Email = email;
        Password = password;
        Role = role;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
