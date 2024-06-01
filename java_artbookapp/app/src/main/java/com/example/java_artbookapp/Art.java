package com.example.java_artbookapp;

import android.graphics.Bitmap;

import java.sql.Blob;

public class Art {

    int id;
    String name,artisName,year;
    Bitmap image;


    public Art(int id, String name, String artisName, String year, Bitmap image) {
        this.id = id;
        this.name = name;
        this.artisName = artisName;
        this.year = year;
        this.image = image;
    }
}
