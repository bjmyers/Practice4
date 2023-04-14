package com.example.practice4.data;

/**
 * To store properly in the NoSQL database, ProductDetails objects are stored and are keyed
 * according to their ID.
 */
public class Product {

    private String id;
    private ProductDetails details;

    public Product() {
    }

    public Product(String id, ProductDetails details) {
        this.id = id;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return details.getName();
    }

    public double getPrice() {
        return details.getPrice();
    }

    public double getChange() {
        return details.getChange();
    }

    public String getLocation() {
        return details.getLocation();
    }
}
