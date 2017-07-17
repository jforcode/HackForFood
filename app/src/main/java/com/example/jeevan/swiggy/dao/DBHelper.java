package com.example.jeevan.swiggy.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jeevan.swiggy.DBTables.OccasionTable;
import com.example.jeevan.swiggy.DBTables.OrderItemTable;
import com.example.jeevan.swiggy.DBTables.OrderTable;
import com.example.jeevan.swiggy.DBTables.MenuItemTable;
import com.example.jeevan.swiggy.DBTables.RestaurantsTable;
import com.example.jeevan.swiggy.DBTables.UserTable;

/**
 * Created by jeevan on 7/15/17.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "SWIGGY";
    static final int dbVersion = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the tables
        // load initial data for restaurants, menu_items, user
        db.execSQL(UserTable.CREATE_QUERY);
        db.execSQL(RestaurantsTable.CREATE_QUERY);
        db.execSQL(MenuItemTable.CREATE_QUERY);
        db.execSQL(OrderTable.CREATE_QUERY);
        db.execSQL(OrderItemTable.CREATE_QUERY);
        db.execSQL(OccasionTable.CREATE_QUERY);

        db.execSQL(UserTable.INIT_INSERT);
        db.execSQL(RestaurantsTable.INIT_INSERT);
        db.execSQL(MenuItemTable.INIT_INSERT);
        db.execSQL(OccasionTable.INIT_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.DELETE_QUERY);
        db.execSQL(RestaurantsTable.DELETE_QUERY);
        db.execSQL(MenuItemTable.DELETE_QUERY);
        db.execSQL(OrderTable.DELETE_QUERY);
        db.execSQL(OrderItemTable.DELETE_QUERY);
        db.execSQL(OccasionTable.DELETE_QUERY);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserTable.DELETE_QUERY);
        db.execSQL(RestaurantsTable.DELETE_QUERY);
        db.execSQL(MenuItemTable.DELETE_QUERY);
        db.execSQL(OrderTable.DELETE_QUERY);
        db.execSQL(OrderItemTable.DELETE_QUERY);
        db.execSQL(OccasionTable.DELETE_QUERY);
        onCreate(db);
    }
}
