package com.example.jeevan.swiggy.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by jeevan on 7/15/17.
 */

public class OrderItem implements Comparable<OrderItem>, Parcelable {
    long id, orderId;
    MenuItem menuItem;
    long qty;

    public OrderItem() {

    }

    protected OrderItem(Parcel in) {
        id = in.readLong();
        orderId = in.readLong();
        menuItem = in.readParcelable(MenuItem.class.getClassLoader());
        qty = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(orderId);
        dest.writeParcelable(menuItem, flags);
        dest.writeLong(qty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    @Override
    public int compareTo(@NonNull OrderItem o) {
        if (id == o.getId()) return 0;
        if (id < o.getId()) return -1;
        return 1;
    }
}
