package com.example.java_historybookapp.viewmodels;

import com.example.java_historybookapp.models.Art;
import com.example.java_historybookapp.models.User;

import java.util.List;

public class UserSingleton {

    private User user;
    private static UserSingleton singleton;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserSingleton getInstance(){
        if(singleton == null){
            singleton = new UserSingleton();
        }
        return singleton;
    }
}
