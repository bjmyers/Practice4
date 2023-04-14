package com.example.practice4.data;

public class ProductDetails {

    private String name;
    private double price;
    private double change;
    private String location;

    public ProductDetails() {
    };

    public ProductDetails(String name, double price, double change, String location) {
        this.name = name;
        this.price = price;
        this.change = change;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public String getLocation() {
        return location;
    }
}
