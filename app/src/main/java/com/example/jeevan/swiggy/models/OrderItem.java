package com.example.jeevan.swiggy.models;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by jeevan on 7/15/17.
 */

public class OrderItem implements Comparable<OrderItem> {
    long id, orderId;
    MenuItem menuItem;
    long qty;


    public OrderItem() {

    }

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
