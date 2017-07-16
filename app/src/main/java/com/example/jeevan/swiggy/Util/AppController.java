package com.example.jeevan.swiggy.Util;

import android.app.Application;
import android.text.TextUtils;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.jeevan.swiggy.activities.BottomTabController;
import com.example.jeevan.swiggy.models.MenuItem;
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
    private BottomTabController bottomTab;
 
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public BottomTabController getBottomTab() {
        return bottomTab;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
        resetOrder();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.order = new Order();
        this.bottomTab = new BottomTabController(getApplicationContext());
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

    public long getQty(long menuItemId) {
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getMenuItem().getId() == menuItemId) {
                return orderItem.getQty();
            }
        }
        return 0;
    }

    public void addItem(MenuItem item) {
        // it means add one qty of given item
        boolean found = false;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getMenuItem().getId() == item.getId()) {
                order.setQty(order.getQty() + 1);
                order.setTotalCost(order.getTotalCost() + item.getPrice());
                orderItem.setQty(orderItem.getQty() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQty(1);
            orderItem.setMenuItem(item);
            order.getOrderItems().add(orderItem);
            order.setQty(order.getQty() + 1);
            order.setTotalCost(order.getTotalCost() + item.getPrice());

        }
        // update the bottom layout
        bottomTab.updateView();
    }

    public void minusItem(MenuItem item) {
        // remove one qty of the item
        int ind = 0, indToRemove = -1;
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getMenuItem().getId() == item.getId()) {
                order.setQty(order.getQty() - 1);
                order.setTotalCost(order.getTotalCost() - item.getPrice());
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
            order.getOrderItems().remove(indToRemove);
        }
        bottomTab.updateView();
    }

    public void resetOrder() {
        order.setOccasion(null);
        order.getOrderItems().clear();
        order.setTotalCost(0);
        order.setQty(0);
        bottomTab.updateView();
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

        bottomTab.updateView();
    }
}