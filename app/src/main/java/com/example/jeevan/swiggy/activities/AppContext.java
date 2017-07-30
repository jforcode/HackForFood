package com.example.jeevan.swiggy.activities;

import android.app.Application;

import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Order;
import com.example.jeevan.swiggy.models.User;

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

}