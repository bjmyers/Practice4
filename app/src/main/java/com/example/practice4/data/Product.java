package com.example.practice4.data;

public class Product {

    private int id;
    private String name;
    private double price;
    private double change;
    private String location;

    public Product() {
    }

    public Product(int id, String name, double price, double change, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.change = change;
        this.location = location;
    }

    public int getId() {
        return id;
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
