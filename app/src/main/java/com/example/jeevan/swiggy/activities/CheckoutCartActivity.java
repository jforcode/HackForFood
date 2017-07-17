package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.UpdateParentInterface;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.adapters.CheckoutAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Order;
import com.example.jeevan.swiggy.models.OrderItem;
import com.example.jeevan.swiggy.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutCartActivity extends AppCompatActivity implements UpdateParentInterface{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_name)
    EditText txtOrderName;
    @BindView(R.id.txt_total)
    TextView txtTotalBill;
    @BindView(R.id.list_order_items)
    RecyclerView listOrderItems;

    CheckoutAdapter adapter;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_cart);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Summary");
        order = AppController.getInstance().getOrder();
        txtTotalBill.setText("\u20B9 " + order.getTotalCost());
        adapter = new CheckoutAdapter(this, order.getOrderItems());
        listOrderItems.setAdapter(adapter);
        listOrderItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btn_order)
    public void saveOrder(View view) {
        User user = AppController.getInstance().getUser();
        Occasion occasion = AppController.getInstance().getOccasion();
        order.setUserId(user.getUserId());
        order.setOccasion(occasion.getOccasion());
        order.setTime(System.currentTimeMillis());
        String orderName = txtOrderName.getText().toString();
        if (orderName.isEmpty()) {
            orderName = user.getUserName() + "-" + occasion.getOccasion();
        }
        order.setOrderName(orderName);
        DBTransactions.getInstance(this).saveOrder(order);
        if (order.getId() != -1) {
            Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OccasionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
    public void update(OrderItem item, long prevQty) {
        for (int i=0;i<order.getOrderItems().size();i++) {
            OrderItem currItem = order.getOrderItems().get(i);
                if (currItem.getId() == item.getId()) {
                long diff = -prevQty + item.getQty();
                order.setQty(order.getQty() + diff);
                order.setTotalCost(order.getTotalCost() + diff*item.getMenuItem().getPrice());
                currItem.setQty(item.getQty());
                if (item.getQty() == 0) {
                    order.getOrderItems().remove(i);
                }
                // same ref, so no update there..
                AppController.getInstance().getBottomTab().updateView();
                break;
            }
        }
        txtTotalBill.setText("\u20B9 " + order.getTotalCost());
    }
}
