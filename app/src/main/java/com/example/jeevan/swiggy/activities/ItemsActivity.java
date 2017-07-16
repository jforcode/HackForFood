package com.example.jeevan.swiggy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.adapters.MenuItemAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rest_name)
    TextView txtRestName;
    @BindView(R.id.rest_cuisines)
    TextView txtRestCuisines;
    @BindView(R.id.list_menu_items)
    RecyclerView listMenuItems;
    @BindView(R.id.bottom_order_layout)
    View bottomLayout;

    Restaurant restaurant;
    Occasion occasion;
    MenuItemAdapter adapter;
    DBTransactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        restaurant = getIntent().getParcelableExtra(Constants.IP_RESTAURANT);
        occasion = AppController.getInstance().getOccasion();
        transactions = DBTransactions.getInstance(this);
        AppController.getInstance().getBottomTab().setBottomTabView(bottomLayout);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRestName.setText(restaurant.getName().toUpperCase());
        txtRestCuisines.setText(Util.getString(restaurant.getCuisines()));

        adapter = new MenuItemAdapter(this);
        listMenuItems.setAdapter(adapter);
        listMenuItems.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(transactions.getMenuItems(restaurant));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: onBackPressed();
                break;
        }
        return true;
    }
}
