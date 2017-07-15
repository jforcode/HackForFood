package com.example.jeevan.swiggy.DBTables;

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
            "(1, \"CHICKEN WINGS\", 195)," +
            "(1, \"BLUEBERRY CHEESE CAKE\", 120)," +
            "(1, \"FRIES\", 105)," +
            "(2, \"GADBAD SUNDAE\", 150)," +
            "(2, \"BLACK MAGIC\", 130)," +
            "(2, \"ARABIAN DATES ICE CREAM\", 80)," +
            "(3, \"BIRYANI\", 200)," +
            "(3, \"MINI TIFFIN\", 115)," +
            "(3, \"IDLI\", 50)";
}
