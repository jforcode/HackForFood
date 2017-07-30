package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.adapters.HomeOrderAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * this is where all bulk orders are showed in different sections like trending, saved etc.
 */
public class OrderHomeActivity extends AppCompatActivity {
    @BindView(R.id.parent)
    CoordinatorLayout parent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_trending_orders)
    RecyclerView listTrendingOrders;

    DBTransactions dbTransactions;
    HomeOrderAdapter orderAdapter;
    long userId;
    String occasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        occasion = AppContext.getInstance().getOccasion().getOccasion();
        userId = AppContext.getInstance().getUser().getUserId();
        getSupportActionBar().setTitle(occasion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderAdapter = new HomeOrderAdapter(this);
        listTrendingOrders.setAdapter(orderAdapter);
        listTrendingOrders.setLayoutManager(new LinearLayoutManager(this));

        fillLists();
    }

    private void fillLists() {
        List<Order> trendingOrders = dbTransactions.getTrendingOrders(userId, occasion, -1);
        orderAdapter.setListOrders(trendingOrders);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.showSummarySnackbar(this, parent);
    }

    @OnClick(R.id.btn_custom_order)
    public void customOrder(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
