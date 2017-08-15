package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.adapters.OrderDetailsAdapter;
import com.example.jeevan.swiggy.interfaces.UpdateParentInterface;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Order;
import com.example.jeevan.swiggy.models.OrderItem;
import com.example.jeevan.swiggy.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutCartActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_name)
    EditText txtOrderName;
    @BindView(R.id.txt_total)
    TextView txtTotalBill;
    @BindView(R.id.list_order_items)
    RecyclerView listOrderItems;

    OrderDetailsAdapter adapter;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_cart);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Summary");
        order = AppContext.getInstance().getOrder();
        txtTotalBill.setText("\u20B9 " + order.getTotalCost());
        adapter = new OrderDetailsAdapter(this, order.getOrderItems());
        listOrderItems.setAdapter(adapter);
        listOrderItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btn_order)
    public void saveOrder(View view) {
        // to remove all activities on stack
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
