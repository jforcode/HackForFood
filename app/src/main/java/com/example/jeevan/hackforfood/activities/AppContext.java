package com.example.jeevan.hackforfood.activities;

import android.app.Application;

import com.example.jeevan.hackforfood.models.MenuItem;
import com.example.jeevan.hackforfood.models.Occasion;
import com.example.jeevan.hackforfood.models.Order;
import com.example.jeevan.hackforfood.models.User;

public class AppContext extends Application {
    private static AppContext mInstance;

    private Order order;
    private User user;
    private Occasion occasion;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void init(User user) {
        this.user = user;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
        this.order = new Order();
    }

    public User getUser() {
        return user;
    }

    public Order getOrder() {
        return order;
    }

    public static synchronized AppContext getInstance() {
        return mInstance;
    }

    public long getCurrQtyForItem(long menuItemId) {
        return order.getQtyForItem(menuItemId);
    }

    public void increaseQtyForItem(MenuItem menuItem) {
        // increase the qty for this item, if it is not present in the order, add it
        order.increaseItemQty(menuItem);
    }

    public void decreaseQtyForItem(long itemId) {
        // decrease the qty for this item, if it is reduced to 0, remove it from the order
        order.decreaseItemQty(itemId);
    }

}