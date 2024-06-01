package com.example.java_currencyapp;

public class Currency {


    public String id;
    public String currencyName;
    public String type;
    public double buying;
    public double selling;
    public double change;

    public Currency(String id,String currencyName, String type, double buying, double selling, double change) {
        this.id = id;
        this.currencyName = currencyName;
        this.type = type;
        this.buying = buying;
        this.selling = selling;
        this.change = change;
    }
}
