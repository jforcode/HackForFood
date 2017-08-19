package com.example.jeevan.hackforfood.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class RestaurantsTable {
    // rest_id, rest_name, cuisine, occasions
    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_CUISINES = "CUISINES";
    public static final String KEY_OCCASIONS = "OCCASIONS";
    public static final String TABLE_NAME = "RESTAURANTS";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NAME + " TEXT NOT NULL," +
            KEY_CUISINES + " TEXT NOT NULL," +
            KEY_OCCASIONS + " TEXT NOT NULL" +
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String INIT_INSERT = "INSERT INTO " + TABLE_NAME +
            "(" + KEY_NAME + ", " + KEY_CUISINES + ", " + KEY_OCCASIONS + ") " +
            " VALUES " +
            "(\"Truffles\", \"Cafe,American,Pizza\", \"Hangout\")," +
            "(\"Polar Bear\", \"Desserts,Fast Food\", \"Hangout,Birthday,Treat\")," +
            "(\"A2B\", \"North Indian,Chinese\", \"Regulars\")";

}
