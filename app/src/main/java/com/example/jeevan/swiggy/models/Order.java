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


    public void addItem(MenuItem item) {
        // it means add one qty of given item
        boolean found = false;
        for (OrderItem orderItem : this.getOrderItems()) {
            if (orderItem.getMenuItem().getId() == item.getId()) {
                this.setQty(this.getQty() + 1);
                this.setTotalCost(this.getTotalCost() + item.getPrice());
                orderItem.setQty(orderItem.getQty() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQty(1);
            orderItem.setMenuItem(item);
            this.getOrderItems().add(orderItem);
            this.setQty(this.getQty() + 1);
            this.setTotalCost(this.getTotalCost() + item.getPrice());

        }
        // update the bottom layout
        
    }

    public void minusItem(MenuItem item) {
        // remove one qty of the item
        int ind = 0, indToRemove = -1;
        for (OrderItem orderItem : this.getOrderItems()) {
            if (orderItem.getMenuItem().getId() == item.getId()) {
                this.setQty(this.getQty() - 1);
                this.setTotalCost(this.getTotalCost() - item.getPrice());
                orderItem.setQty(orderItem.getQty() - 1);
                if (orderItem.getQty() == 0) {
                    // delete thix item
                    indToRemove = ind;
                }
                break;
            }
            ind++;
        }
        if (indToRemove > -1) {
            this.getOrderItems().remove(indToRemove);
        }
    }

    public void resetOrder() {
        this.setOccasion(null);
        this.getOrderItems().clear();
        this.setTotalCost(0);
        this.setQty(0);
        
    }

    // order modifying functions
    public void mergeOrder(Order otherOrder) {
        if (otherOrder == null) return;
        // merge the given order into the current order
        Collections.sort(this.getOrderItems());
        Collections.sort(otherOrder.getOrderItems());
        ArrayList<OrderItem> toAdd = new ArrayList<>();
        int orderSize = this.getOrderItems().size();
        int otherOrderSize = otherOrder.getOrderItems().size();
        int i=0, j=0;
        long qty = 0;
        for (;i<orderSize && j<otherOrderSize;) {
            OrderItem orderItem = this.getOrderItems().get(i);
            OrderItem otherOrderItem = otherOrder.getOrderItems().get(j);
            int comp = orderItem.compareTo(otherOrderItem);
            if (comp == 0) {
                orderItem.setQty(orderItem.getQty() + otherOrderItem.getQty());
                qty += orderItem.getQty();
                i++;
                j++;
            } else if (comp < 0) {
                i++;
                qty += orderItem.getQty();
            } else {
                toAdd.add(otherOrderItem);
                qty += otherOrderItem.getQty();
                j++;
            }
        }
        this.getOrderItems().addAll(toAdd);
        for (;j<otherOrderSize;j++) {
            this.getOrderItems().add(otherOrder.getOrderItems().get(j));
            qty += otherOrder.getOrderItems().get(j).getQty();
        }
        this.setTotalCost(this.getTotalCost() + otherOrder.getTotalCost());
        this.setQty(qty);
        
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
}
