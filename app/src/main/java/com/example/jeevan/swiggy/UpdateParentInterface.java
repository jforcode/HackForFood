package com.example.jeevan.swiggy;

import com.example.jeevan.swiggy.models.OrderItem;

/**
 * Created by jeevan on 7/16/17.
 */

public interface UpdateParentInterface {
    void update(OrderItem item, long prevQty);
}
