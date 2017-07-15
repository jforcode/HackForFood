package com.example.jeevan.swiggy.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class OrderTable {
    // occasion, order_id, user_id, total_cost, order_time
    public static final String KEY_ID = "ID";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_OCCASION = "OCCASION";
    public static final String KEY_TOTAL_COST = "TOTAL_COST";
    public static final String KEY_TIME = "ORDER_TIME";

    public static final String TABLE_NAME = "ORDER";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_USER_ID + " INTEGER NOT NULL," +
            KEY_OCCASION + " TEXT," +
            KEY_TOTAL_COST + " REAL NOT NULL," +
            KEY_TIME + " INTEGER NOT NULL" +
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
