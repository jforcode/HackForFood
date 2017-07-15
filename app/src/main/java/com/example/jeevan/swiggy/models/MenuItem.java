package com.example.jeevan.swiggy.models;

/**
 * Created by jeevan on 7/15/17.
 */

public class MenuItem {
    // describes a food menuItem
    long id;
    String name;
    long restId;
    double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRestId() {
        return restId;
    }

    public void setRestId(long restId) {
        this.restId = restId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
