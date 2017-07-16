package com.example.jeevan.swiggy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by jeevan on 7/15/17.
 */

public class Restaurant implements Comparable<Restaurant>, Parcelable {
    // describes a restaurant
    long id;
    String name;
    String[] cuisines;
    String[] occasions;

    public Restaurant() {

    }

    protected Restaurant(Parcel in) {
        id = in.readLong();
        name = in.readString();
        cuisines = in.createStringArray();
        occasions = in.createStringArray();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String[] getOccasions() {
        return occasions;
    }

    public void setOccasions(String[] occasions) {
        this.occasions = occasions;
    }


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
    public int compareTo(@NonNull Restaurant o) {
        if (id == o.getId()) return 0;
        if (id < o.getId()) return -1;
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Restaurant)) return false;
        if (id == ((Restaurant) obj).getId()) return true;
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeStringArray(cuisines);
        dest.writeStringArray(occasions);
    }
}
