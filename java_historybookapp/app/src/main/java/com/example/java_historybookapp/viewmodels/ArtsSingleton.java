package com.example.java_historybookapp.viewmodels;

import com.example.java_historybookapp.models.Art;

import java.util.List;

public class ArtsSingleton {

    private List<Art> artList;
    private static ArtsSingleton singleton;

    public List<Art> getArtList() {
        return artList;
    }

    public void setArtList(List<Art> artList) {
        this.artList = artList;
    }

    public static ArtsSingleton getInstance(){
        if(singleton == null){
            singleton = new ArtsSingleton();
        }
        return singleton;
    }
}
