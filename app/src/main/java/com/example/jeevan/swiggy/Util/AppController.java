package com.example.jeevan.swiggy.Util;

import android.app.Application;
import android.text.TextUtils;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Order;
import com.example.jeevan.swiggy.models.OrderItem;
import com.example.jeevan.swiggy.models.User;

import java.util.ArrayList;
import java.util.Collections;

public class AppController extends Application {
    private static AppController mInstance;

    private Order order;
    private User user;
    private Occasion occasion;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
        if (occasion == null) {
            order = null;
        } else {
            order = new Order();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    // order modifying functions
    public void mergeOrder(Order otherOrder) {
        if (otherOrder == null) return;
        if (this.order == null) {
            this.order = otherOrder;
            return;
        }
        // merge the given order into the current order
        Collections.sort(order.getOrderItems());
        Collections.sort(otherOrder.getOrderItems());
        ArrayList<OrderItem> toAdd = new ArrayList<>();
        int orderSize = order.getOrderItems().size();
        int otherOrderSize = otherOrder.getOrderItems().size();
        for (int i=0,j=0;i<orderSize && j<otherOrderSize;) {
            OrderItem orderItem = order.getOrderItems().get(i);
            OrderItem otherOrderItem = otherOrder.getOrderItems().get(j);
            int comp = orderItem.compareTo(otherOrderItem);
            if (comp == 0) {
                orderItem.setQty(orderItem.getQty() + otherOrderItem.getQty());
                i++;
                j++;
            } else if (comp < 0) {
                i++;
            } else {
                toAdd.add(otherOrderItem);
                j++;
            }
        }
        order.getOrderItems().addAll(toAdd);
        order.setTotalCost(order.getTotalCost() + otherOrder.getTotalCost());
    }
}