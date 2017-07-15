package com.example.jeevan.swiggy.Util;

import com.example.jeevan.swiggy.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeevan on 7/15/17.
 */

public class Transactions {
    // a util class to return dummy data.
    // maybe can extend to include a local database for data from swiggy.
    public static List<Order> getTopOrders() {
        // create dummy orders
        Order savedOrder = new Order();
        List<Order> topOrders = new ArrayList<>();
        topOrders.add(savedOrder);
        return topOrders;
    }
}
