package com.example.jeevan.swiggy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeevan.swiggy.DBTables.MenuItemTable;
import com.example.jeevan.swiggy.DBTables.OrderItemTable;
import com.example.jeevan.swiggy.DBTables.OrderTable;
import com.example.jeevan.swiggy.DBTables.RestaurantsTable;
import com.example.jeevan.swiggy.models.MenuItem;
import com.example.jeevan.swiggy.models.Order;
import com.example.jeevan.swiggy.models.OrderItem;
import com.example.jeevan.swiggy.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeevan on 7/15/17.
 */

public class DBTransactions {
    private Context context;
    private static DBTransactions instance;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private DBTransactions(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public static DBTransactions getInstance(Context context) {
        if (instance == null) {
            instance = new DBTransactions(context.getApplicationContext());
        }
        return instance;
    }

    // getting from cursor
    private Restaurant getRestaurantFromCursor(Cursor cursor) {
        Restaurant r = new Restaurant();
        r.setId(cursor.getLong(cursor.getColumnIndex(RestaurantsTable.KEY_ID)));
        r.setName(cursor.getString(cursor.getColumnIndex(RestaurantsTable.KEY_NAME)));
        r.setOccasions(cursor.getString(cursor.getColumnIndex(RestaurantsTable.KEY_OCCASIONS)).split(","));
        r.setCuisines(cursor.getString(cursor.getColumnIndex(RestaurantsTable.KEY_CUISINES)).split(","));
        return r;
    }

    private MenuItem getMenuItemFromCursor(Cursor cursor) {
        MenuItem item = new MenuItem();
        item.setId(cursor.getLong(cursor.getColumnIndex(MenuItemTable.KEY_ID)));
        item.setRestId(cursor.getLong(cursor.getColumnIndex(MenuItemTable.KEY_REST_ID)));
        item.setName(cursor.getString(cursor.getColumnIndex(MenuItemTable.KEY_NAME)));
        item.setPrice(cursor.getDouble(cursor.getColumnIndex(MenuItemTable.KEY_PRICE)));
        return item;
    }

    private Order getOrderFromCursor(Cursor cursor) {
        Order order = new Order();
        order.setId(cursor.getLong(cursor.getColumnIndex(OrderTable.KEY_ID)));
        order.setUserId(cursor.getLong(cursor.getColumnIndex(OrderTable.KEY_USER_ID)));
        order.setOccasion(cursor.getString(cursor.getColumnIndex(OrderTable.KEY_OCCASION)));
        order.setTime(cursor.getLong(cursor.getColumnIndex(OrderTable.KEY_TIME)));
        order.setTotalCost(cursor.getDouble(cursor.getColumnIndex(OrderTable.KEY_TOTAL_COST)));
        order.setOrderItems(getOrderItems(order.getId()));
        return order;
    }

    private OrderItem getOrderItemFromCursor(Cursor cursor) {
        OrderItem oe = new OrderItem();
        oe.setId(cursor.getLong(cursor.getColumnIndex(OrderItemTable.KEY_ID)));
        oe.setOrderId(cursor.getLong(cursor.getColumnIndex(OrderItemTable.KEY_ORDER_ID)));
        oe.setQty(cursor.getLong(cursor.getColumnIndex(OrderItemTable.KEY_QTY)));
        long itemId = cursor.getLong(cursor.getColumnIndex(OrderItemTable.KEY_ITEM_ID));
        oe.setMenuItem(getMenuItem(itemId));
        return oe;
    }

    public List<Restaurant> getAllRestaurants(String searchParam) {
        List<Restaurant> restaurants = new ArrayList<>();
        String where = RestaurantsTable.KEY_OCCASIONS + " LIKE '%" + searchParam + "%' OR " +
                RestaurantsTable.KEY_NAME + " LIKE '%" + searchParam + "%' OR " +
                RestaurantsTable.KEY_CUISINES + " LIKE '%" + searchParam + "%'";
        String[] args = {};
        Cursor cursor = db.query(RestaurantsTable.TABLE_NAME, null, where, args, null, null, null);
        while (cursor.moveToNext()) {
            restaurants.add(getRestaurantFromCursor(cursor));
        }
        if (cursor != null) cursor.close();
        String query = "SELECT DISTINCT RT.* FROM " + RestaurantsTable.TABLE_NAME + " RT, " + MenuItemTable.TABLE_NAME + " MIT " +
                " WHERE RT." + RestaurantsTable.KEY_ID + " = MIT." + MenuItemTable.KEY_ID +
                " AND MIT." + MenuItemTable.KEY_NAME + " LIKE '%" + searchParam + "%'";
        cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Restaurant r = getRestaurantFromCursor(cursor);
            if (!restaurants.contains(r)) {
                restaurants.add(r);
            }
        }
        if (cursor != null) cursor.close();
        return restaurants;
    }

    public MenuItem getMenuItem(long itemId) {
        MenuItem me = null;
        String where = MenuItemTable.KEY_ID + " = ?";
        String[] args = {String.valueOf(itemId)};
        Cursor cursor = db.query(MenuItemTable.TABLE_NAME, null, where, args, null, null, null);
        if (cursor.moveToNext()) {
            me = getMenuItemFromCursor(cursor);
        }
        if (cursor != null) cursor.close();
        return me;
    }
    
    public List<OrderItem> getOrderItems(long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String where = "";
        String[] args = {};
        String order = "";
        Cursor cursor = db.query(OrderItemTable.TABLE_NAME, null, where, args, null, null, order);
        while (cursor.moveToNext()) {
            OrderItem oe = getOrderItemFromCursor(cursor);
        }
        return orderItems;
    }

    public List<Order> getPreviousOrders(long userId, String occasion, long limit) {
        List<Order> ret = new ArrayList<>();
        String where = OrderTable.KEY_USER_ID + " = ? AND " + OrderTable.KEY_OCCASION + " = ?";
        String[] args = {String.valueOf(userId), occasion};
        String order = OrderTable.KEY_ID + " DESC";
        Cursor cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order, String.valueOf(limit));
        int count = 0;
        while (cursor.moveToNext() && count++ < limit) {
            ret.add(getOrderFromCursor(cursor));
        }
        if (cursor != null) cursor.close();
        return ret;
    }

    public List<Order> getTrendingOrders(String occasion, int limit) {
        List<Order> ret = new ArrayList<>();
        String where = OrderTable.KEY_OCCASION + " = ?";
        String[] args = {occasion};
        String order = OrderTable.KEY_ID + " DESC";
        Cursor cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order, String.valueOf(limit));
        while (cursor.moveToNext()) {
            ret.add(getOrderFromCursor(cursor));
        }
        if (cursor != null) cursor.close();
        return ret;
    }

    private ContentValues getContentValues(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderTable.KEY_USER_ID, order.getUserId());
        values.put(OrderTable.KEY_OCCASION, order.getOccasion());
        values.put(OrderTable.KEY_TIME, order.getTime());
        values.put(OrderTable.KEY_TOTAL_COST, order.getTotalCost());
        return values;
    }

    private ContentValues getContentValues(OrderItem orderItem) {
        ContentValues values = new ContentValues();
        values.put(OrderItemTable.KEY_ITEM_ID, orderItem.getMenuItem().getId());
        values.put(OrderItemTable.KEY_ORDER_ID, orderItem.getOrderId());
        values.put(OrderItemTable.KEY_QTY, orderItem.getQty());
        return values;
    }

    public void saveOrder(Order order) {
        ContentValues values = getContentValues(order);
        long id = db.insert(OrderTable.TABLE_NAME, null, values);
        order.setId(id);
        for (OrderItem orderItem : order.getOrderItems()) {
            values = getContentValues(orderItem);
            id = db.insert(OrderItemTable.TABLE_NAME, null, values);
            orderItem.setId(id);
        }
    }
}
