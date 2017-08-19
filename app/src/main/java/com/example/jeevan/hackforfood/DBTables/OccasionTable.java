package com.example.jeevan.hackforfood.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class OccasionTable {
    public static final String KEY_ID = "ID";
    public static final String KEY_OCCASION = "OCCASION";
    public static final String KEY_TERMS = "TERMS";
    public static final String KEY_DESC = "DESC";

    public static final String TABLE_NAME = "OCASSION";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_OCCASION + " TEXT NOT NULL," +
            KEY_DESC + " TEXT," +
            KEY_TERMS + " TEXT" +
            ")";
    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String INIT_INSERT = "INSERT INTO " + TABLE_NAME +
            "(" + KEY_OCCASION + ", " + KEY_TERMS + ", " + KEY_DESC + ")" +
            " VALUES " +
            "(\"Hangout\", \"Pizza,Rolls,Beverages,Burger\", \"Chilling with friends, order a quick bite!\")," +
            "(\"Regulars\", \"Meals,Breakfast,Dinner\", \"Order the everyday regular!\")";
}
