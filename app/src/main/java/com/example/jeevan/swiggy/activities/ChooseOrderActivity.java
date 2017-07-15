package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.Util.Transactions;
import com.example.jeevan.swiggy.adapters.OrderHorizontalAdapter;
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
    @BindView(R.id.list_saved)
    RecyclerView listSavedOrders;

    OrderHorizontalAdapter savedAdapter;
    Order savedOrder;

    String occasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_order);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        occasion = getIntent().getExtras().getString(Constants.IP_OCCASION_NAME);
        if (occasion == null) {
            Toast.makeText(this, "Didn't get occasion in ChooseOrderActivity", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
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
        List<Order> topOrders = Transactions.getTopOrders();
        savedOrder = topOrders.get(0);
        savedAdapter.setOrderItems(savedOrder.getOrderItems());

    }

    @OnClick(R.id.create_custom)
    public void createCustomOrder(View view) {
        Intent intent = new Intent(this, CreateCustomOrderActivity.class);
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
}
