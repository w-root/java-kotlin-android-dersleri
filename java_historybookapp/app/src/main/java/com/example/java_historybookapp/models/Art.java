package com.example.java_historybookapp.models;

public class Art
{
    private int Id;
    private String Name;
    private String Description;
    private String Author;
    private String Year;
    private String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public Art(int id, String name, String description, String author, String year, String location) {
        Id = id;
        Name = name;
        Description = description;
        Author = author;
        Year = year;
        Location = location;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
