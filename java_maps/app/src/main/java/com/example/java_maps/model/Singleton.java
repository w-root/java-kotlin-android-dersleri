package com.example.java_maps.model;

import io.reactivex.rxjava3.core.Single;

public class Singleton {

    private Place place;
    private static Singleton singleton;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
