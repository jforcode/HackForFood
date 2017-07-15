package com.example.jeevan.swiggy.models;

import java.util.Comparator;

/**
 * Created by jeevan on 7/15/17.
 */

public class Restaurant implements Comparator<Restaurant> {
    // describes a restaurant
    long id;
    String name;
    String[] cuisines;

    public String[] getOccasions() {
        return occasions;
    }

    public void setOccasions(String[] occasions) {
        this.occasions = occasions;
    }

    String[] occasions;

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

    public String[] getCuisines() {
        return cuisines;
    }

    public void setCuisines(String[] cuisines) {
        this.cuisines = cuisines;
    }


    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        if (o1.getId() == o2.getId()) return 0;
        if (o1.getId() < o2.getId()) return -1;
        return 1;
    }
}
