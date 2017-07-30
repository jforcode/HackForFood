package com.example.jeevan.swiggy.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jeevan on 7/15/17.
 */

public class MenuItem implements Parcelable {
    // describes a food menuItem
    long id;
    String name;
    Restaurant restaurant;
    double price;

    public MenuItem() {

    }

    protected MenuItem(Parcel in) {
        id = in.readLong();
        name = in.readString();
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        price = in.readDouble();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeParcelable(restaurant, flags);
        dest.writeDouble(price);
    }
}
