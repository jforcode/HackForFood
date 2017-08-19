package com.example.jeevan.hackforfood.DBTables;

/**
 * Created by jeevan on 7/15/17.
 */

public class UserTable {
    // user_id, name, username, pwd
    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_USERNAME = "USERNAME";
    public static final String KEY_PWD = "PASSWORD";

    public static final String TABLE_NAME = "USER";
    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_USERNAME + " TEXT NOT NULL, " +
            KEY_PWD + " TEXT NOT NULL" +
            ")";

    public static final String DELETE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String INIT_INSERT = "INSERT INTO " + TABLE_NAME +
            "(" + KEY_NAME + ", " + KEY_USERNAME + ", " + KEY_PWD + ")" +
            " VALUES " +
            "(\"Jeevan\", \"Jeevan\", \"Jeevan\")," +
            "(\"Pankaj\", \"Pankaj\", \"Pankaj\")";
}