package com.example.jeevan.hackforfood.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class MenuItemTable {
    // item_id, rest_id, NAME, price
    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_REST_ID = "REST_ID";
    public static final String KEY_PRICE = "PRICE";

    public static final String TABLE_NAME = "REST_ITEMS";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_REST_ID + " INTEGER NOT NULL," +
            KEY_NAME + " TEXT NOT NULL," +
            KEY_PRICE + " REAL NOT NULL" +
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INIT_INSERT = "INSERT INTO " + TABLE_NAME +
            "(" + KEY_REST_ID + ", " + KEY_NAME + ", " + KEY_PRICE + ")" +
            " VALUES " +
            "(1, \"Chicken Wings\", 195)," +
            "(1, \"Blueberry Cheese Cake\", 120)," +
            "(1, \"Fries\", 105)," +
            "(2, \"Gadbad Sundae\", 150)," +
            "(2, \"Black Magic\", 130)," +
            "(2, \"Arabian Dates Ice Cream\", 80)," +
            "(3, \"Biryani\", 200)," +
            "(3, \"Mini Tiffin\", 115)," +
            "(3, \"Idli\", 50)";
}
