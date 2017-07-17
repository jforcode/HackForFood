package com.example.jeevan.swiggy.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
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
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.adapters.OrderHorizontalAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseOrderActivity extends AppCompatActivity {
    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.no_prev_orders)
    TextView noPrevOrders;
    @BindView(R.id.with_prev_orders)
    RelativeLayout layoutPrevOrders;
    @BindView(R.id.list_prev)
    RecyclerView listPrevOrders;
    @BindView(R.id.prev_order_name)
    TextView txtPrevOrderName;
    @BindView(R.id.prev_total_cost)
    TextView txtTotalCost;

    @BindView(R.id.no_comm_orders)
    TextView noCommOrders;
    @BindView(R.id.with_comm_orders)
    RelativeLayout layoutCommOrders;
    @BindView(R.id.list_comm)
    RecyclerView listCommOrders;
    @BindView(R.id.comm_order_name)
    TextView txtCommOrderName;
    @BindView(R.id.comm_total_cost)
    TextView txtCommTotalCost;

    @BindView(R.id.bottom_order_layout)
    View bottomView;

    OrderHorizontalAdapter savedAdapter, commAdapter;
    Order savedOrder, commOrder;
    String occasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_order);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        occasion = AppController.getInstance().getOccasion().getOccasion();
        AppController.getInstance().getBottomTab().setBottomTabView(bottomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(occasion);
        setUpLists();
        fillData();
    }

    private void setUpLists() {
        savedAdapter = new OrderHorizontalAdapter(this);
        listPrevOrders.setAdapter(savedAdapter);
        listPrevOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        commAdapter = new OrderHorizontalAdapter(this);
        listCommOrders.setAdapter(commAdapter);
        listCommOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void fillData() {
        List<Order> topOrders = DBTransactions.getInstance(this).getPreviousOrders(AppController.getInstance().getUser().getUserId(), occasion, 1);
        if (topOrders.size() == 0) {
            noPrevOrders.setVisibility(View.VISIBLE);
            layoutPrevOrders.setVisibility(View.GONE);
        } else {
            noPrevOrders.setVisibility(View.GONE);
            layoutPrevOrders.setVisibility(View.VISIBLE);
            savedOrder = topOrders.get(0);
            savedAdapter.setOrderItems(savedOrder.getOrderItems());
            txtPrevOrderName.setText(savedOrder.getOrderName());
            txtTotalCost.setText("\u20B9 " + Util.getRoundedDouble(savedOrder.getTotalCost()));
        }

        topOrders = DBTransactions.getInstance(this).getTrendingOrders(AppController.getInstance().getUser().getUserId(), occasion, 1);
        if (topOrders.size() == 0) {
            noCommOrders.setVisibility(View.VISIBLE);
            layoutCommOrders.setVisibility(View.GONE);
        } else {
            noCommOrders.setVisibility(View.GONE);
            layoutCommOrders.setVisibility(View.VISIBLE);
            commOrder = topOrders.get(0);
            commAdapter.setOrderItems(commOrder.getOrderItems());
            txtCommOrderName.setText(commOrder.getOrderName());
            txtCommTotalCost.setText("\u20B9 " + Util.getRoundedDouble(commOrder.getTotalCost()));
        }
    }

    @OnClick(R.id.create_custom)
    public void createCustomOrder(View view) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_prev_checkout_order, R.id.btn_comm_checkout_order})
    public void addToCart(View view) {
        AppController.getInstance().getBottomTab().setBottomTabView(bottomView);
        switch(view.getId()) {
            case R.id.btn_prev_checkout_order:
                AppController.getInstance().mergeOrder(savedOrder);
                break;
            case R.id.btn_comm_checkout_order:
                AppController.getInstance().mergeOrder(commOrder);
                break;
        }
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

    @Override
    public void onBackPressed() {
        confirmGoingBack();
    }

    private void confirmGoingBack() {
        new AlertDialog.Builder(this)
                .setMessage("The order placed for " + occasion + " will be lost!\nAre you sure of going back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppController.getInstance().setOccasion(null);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().getBottomTab().setBottomTabView(bottomView);
    }
}
