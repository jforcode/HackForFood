package com.example.jeevan.hackforfood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeevan.hackforfood.DBTables.MenuItemTable;
import com.example.jeevan.hackforfood.DBTables.OccasionTable;
import com.example.jeevan.hackforfood.DBTables.OrderItemTable;
import com.example.jeevan.hackforfood.DBTables.OrderTable;
import com.example.jeevan.hackforfood.DBTables.RestaurantsTable;
import com.example.jeevan.hackforfood.DBTables.UserTable;
import com.example.jeevan.hackforfood.models.MenuItem;
import com.example.jeevan.hackforfood.models.Occasion;
import com.example.jeevan.hackforfood.models.Order;
import com.example.jeevan.hackforfood.models.OrderItem;
import com.example.jeevan.hackforfood.models.Restaurant;
import com.example.jeevan.hackforfood.models.User;

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
        item.setRestaurant(getRestaurant(cursor.getLong(cursor.getColumnIndex(MenuItemTable.KEY_REST_ID))));
        item.setName(cursor.getString(cursor.getColumnIndex(MenuItemTable.KEY_NAME)));
        item.setPrice(cursor.getDouble(cursor.getColumnIndex(MenuItemTable.KEY_PRICE)));
        return item;
    }

    private Order getOrderFromCursor(Cursor cursor) {
        Order order = new Order();
        order.setId(cursor.getLong(cursor.getColumnIndex(OrderTable.KEY_ID)));
        order.setOrderName(cursor.getString(cursor.getColumnIndex(OrderTable.KEY_NAME)));
        order.setUser(getUser(cursor.getLong(cursor.getColumnIndex(OrderTable.KEY_USER_ID))));
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

    private Occasion getOccasionFromCursor(Cursor cursor) {
        Occasion occasion = new Occasion();
        occasion.setId(cursor.getLong(cursor.getColumnIndex(OccasionTable.KEY_ID)));
        occasion.setOccasion(cursor.getString(cursor.getColumnIndex(OccasionTable.KEY_OCCASION)));
        occasion.setDesc(cursor.getString(cursor.getColumnIndex(OccasionTable.KEY_DESC)));
        occasion.setTerms(cursor.getString(cursor.getColumnIndex(OccasionTable.KEY_TERMS)).split(","));
        occasion.setDrawable();
        return occasion;
    }

    public User validateAndGetUser(String un, String pw) {
        User user = null;
        String where = UserTable.KEY_USERNAME + " = ? AND " + UserTable.KEY_PWD + " = ?";
        String[] args = {un, pw};
        Cursor cursor = db.query(UserTable.TABLE_NAME, null, where, args, null, null, null, null);
        if (cursor.moveToNext()) {
            user = new User();
            user.setUserId(cursor.getLong(cursor.getColumnIndex(UserTable.KEY_ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserTable.KEY_NAME)));
        }
        if (cursor != null) cursor.close();
        return user;
    }

    public User getUser(long userId) {
        String where = UserTable.KEY_ID + " = ?";
        String[] args = {String.valueOf(userId)};
        Cursor cursor = db.query(UserTable.TABLE_NAME, null, where, args, null, null, null, null);
        User user = null;
        if (cursor.moveToNext()) {
            user = new User();
            user.setUserId(cursor.getLong(cursor.getColumnIndex(UserTable.KEY_ID)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserTable.KEY_NAME)));
        }
        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    private Restaurant getRestaurant(long restId) {
        Restaurant restaurant = null;
        String where = RestaurantsTable.KEY_ID + " = ?";
        String[] args = {String.valueOf(restId)};
        Cursor cursor = db.query(RestaurantsTable.TABLE_NAME, null, where, args, null, null, null);
        if (cursor.moveToNext()) {
            restaurant = getRestaurantFromCursor(cursor);
        }
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants(String searchParam) {
        List<Restaurant> restaurants = new ArrayList<>();
        String where = RestaurantsTable.KEY_OCCASIONS + " LIKE '%" + searchParam + "%' OR " +
                RestaurantsTable.KEY_NAME + " LIKE '%" + searchParam + "%' OR " +
                RestaurantsTable.KEY_CUISINES + " LIKE '%" + searchParam + "%'";
        String[] args = {};
        Cursor cursor = db.query(RestaurantsTable.TABLE_NAME, null, where, args, null, null, null);
        while (cursor.moveToNext()) {
            Restaurant r = getRestaurantFromCursor(cursor);
            if (!restaurants.contains(r)) {
                restaurants.add(r);
            }
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

    public List<MenuItem> getMenuItems(Restaurant restaurant) {
        List<MenuItem> menuItems = new ArrayList<>();
        String where = MenuItemTable.KEY_REST_ID + " = ?";
        String[] args = {String.valueOf(restaurant.getId())};
        Cursor cursor = db.query(MenuItemTable.TABLE_NAME, null, where, args, null, null, null, null);
        while (cursor.moveToNext()) {
            MenuItem item = new MenuItem();
            item.setId(cursor.getLong(cursor.getColumnIndex(MenuItemTable.KEY_ID)));
            item.setRestaurant(restaurant);
            item.setName(cursor.getString(cursor.getColumnIndex(MenuItemTable.KEY_NAME)));
            item.setPrice(cursor.getDouble(cursor.getColumnIndex(MenuItemTable.KEY_PRICE)));
            menuItems.add(item);
        }
        if (cursor != null) cursor.close();
        return menuItems;
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
        String where = OrderItemTable.KEY_ORDER_ID + " = ?";
        String[] args = {String.valueOf(orderId)};
        Cursor cursor = db.query(OrderItemTable.TABLE_NAME, null, where, args, null, null, null);
        while (cursor.moveToNext()) {
            orderItems.add(getOrderItemFromCursor(cursor));
        }
        return orderItems;
    }

    public List<Order> getPreviousOrders(long userId, String occasion, long limit) {
        List<Order> ret = new ArrayList<>();
        String where = OrderTable.KEY_USER_ID + " = ? AND " + OrderTable.KEY_OCCASION + " = ?";
        String[] args = {String.valueOf(userId), occasion};
        String order = OrderTable.KEY_ID + " DESC";
        Cursor cursor = null;
        if (limit != -1) {
            cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order, String.valueOf(limit));
        } else {
            cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order);
        }
        int count = 0;
        while (cursor.moveToNext() && count++ < limit) {
            ret.add(getOrderFromCursor(cursor));
        }
        if (cursor != null) cursor.close();
        return ret;
    }

    public List<Order> getTrendingOrders(long userId, String occasion, int limit) {
        List<Order> ret = new ArrayList<>();
        String where = OrderTable.KEY_OCCASION + " = ? AND " + OrderTable.KEY_USER_ID + " NOT IN (?)";
        String[] args = {occasion, String.valueOf(userId)};
        String order = OrderTable.KEY_ID + " DESC";
        Cursor cursor = null;
        if (limit != -1) {
            cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order, String.valueOf(limit));
        } else {
            cursor = db.query(OrderTable.TABLE_NAME, null, where, args, null, null, order);
        }
        while (cursor.moveToNext()) {
            ret.add(getOrderFromCursor(cursor));
        }
        if (cursor != null) cursor.close();
        return ret;
    }

    public List<Occasion> getAllOccasions() {
        List<Occasion> occasions = new ArrayList<>();
        Cursor cursor = db.query(OccasionTable.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            occasions.add(getOccasionFromCursor(cursor));
        }
        return occasions;
    }

    private ContentValues getContentValues(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderTable.KEY_NAME, order.getOrderName());
        values.put(OrderTable.KEY_USER_ID, order.getUser().getUserId());
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
            orderItem.setOrderId(order.getId());
            values = getContentValues(orderItem);
            id = db.insert(OrderItemTable.TABLE_NAME, null, values);
            orderItem.setId(id);
        }
    }
}
