package com.example.jeevan.swiggy.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeevan on 7/15/17.
 */

public class Order implements Parcelable {
    // describe a custom swiggy order
    long id;
    User user;
    // should change this to map
    List<OrderItem> orderItems;
    long qty;
    String orderName;
    String occasion;
    double totalCost;
    long time;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    protected Order(Parcel in) {
        id = in.readLong();
        orderItems = in.createTypedArrayList(OrderItem.CREATOR);
        qty = in.readLong();
        orderName = in.readString();
        occasion = in.readString();
        totalCost = in.readDouble();
        time = in.readLong();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeTypedList(orderItems);
        dest.writeLong(qty);
        dest.writeString(orderName);
        dest.writeString(occasion);
        dest.writeDouble(totalCost);
        dest.writeLong(time);
    }

    public long getQtyForItem(long menuItemId) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getMenuItem().getId() == menuItemId) {
                return orderItem.getQty();
            }
        }
        return 0;
    }

    public void increaseItemQty(MenuItem item) {
        if (item == null) {
            return;
        }
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getMenuItem().getId() == item.getId()) {
                orderItem.setQty(orderItem.getQty() + 1);
                qty++;
                totalCost += item.getPrice();
                return;
            }
        }
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setQty(1);
        newOrderItem.setMenuItem(item);
        newOrderItem.setOrderId(id);
        orderItems.add(newOrderItem);
        qty++;
        totalCost += item.getPrice();
    }

    public void decreaseItemQty(long itemId) {
        if (itemId == -1) {
            return;
        }
        for (int i=0;i<orderItems.size();i++) {
            OrderItem orderItem = orderItems.get(i);
            if (orderItem.getMenuItem().getId() == itemId) {
                double price = orderItem.getMenuItem().getPrice();
                if (orderItem.getQty() == 1) {
                    orderItems.remove(i);
                } else {
                    orderItem.setQty(orderItem.getQty() - 1);
                }
                qty--;
                totalCost -= price;
                return;
            }
        }
    }
}
