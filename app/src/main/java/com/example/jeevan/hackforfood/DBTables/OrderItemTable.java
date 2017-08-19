package com.example.jeevan.hackforfood.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class OrderItemTable {
    // order_item_id, order_id, item_id, qty
    public static final String KEY_ID = "ID";
    public static final String KEY_ORDER_ID = "ORDER_ID";
    public static final String KEY_ITEM_ID = "ITEM_ID";
    public static final String KEY_QTY = "QTY";

    public static final String TABLE_NAME = "ORDER_ITEM";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_ORDER_ID + " INTEGER NOT NULL," +
            KEY_ITEM_ID + " INTEGER NOT NULL," +
            KEY_QTY + " INTEGER NOT NULL" +
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
