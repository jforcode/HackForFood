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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.Util.Constants;
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
    @BindView(R.id.list_previous)
    RecyclerView listSavedOrders;
    @BindView(R.id.order_name)
    TextView txtOrderName;
    @BindView(R.id.total_cost)
    TextView txtTotalCost;
    @BindView(R.id.bottom_order_layout)
    View bottomView;

    OrderHorizontalAdapter savedAdapter;
    Order savedOrder;
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
        // make jsonObject request for the top lists for all three
        // for now, using dummy data.
        savedAdapter = new OrderHorizontalAdapter(this);
        listSavedOrders.setAdapter(savedAdapter);
        listSavedOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void fillData() {
        List<Order> topOrders = DBTransactions.getInstance(this).getPreviousOrders(1, occasion, 1);
        if (topOrders.size() == 0) {
            noPrevOrders.setVisibility(View.VISIBLE);
            layoutPrevOrders.setVisibility(View.GONE);
        } else {
            noPrevOrders.setVisibility(View.GONE);
            layoutPrevOrders.setVisibility(View.VISIBLE);
            savedOrder = topOrders.get(0);
            savedAdapter.setOrderItems(savedOrder.getOrderItems());
            txtOrderName.setText(savedOrder.getOrderName());
            txtTotalCost.setText(Util.getRoundedDouble(savedOrder.getTotalCost()) + "");
        }
    }

    @OnClick(R.id.create_custom)
    public void createCustomOrder(View view) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_show_more_saved})
    public void showMore(View view) {
        switch (view.getId()) {
            case R.id.btn_show_more_saved:
                break;
            default:
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
                .setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppController.getInstance().setOccasion(null);
                        finish();
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
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
