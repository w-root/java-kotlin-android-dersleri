package com.example.java_artbookapp;

public class Singleton {

    private Art art;
    private static Singleton singleton;

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }

}
