package com.example.jeevan.hackforfood.activities;

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

import com.example.jeevan.hackforfood.R;
import com.example.jeevan.hackforfood.Util.Constants;
import com.example.jeevan.hackforfood.adapters.OrderDetailsAdapter;
import com.example.jeevan.hackforfood.interfaces.UpdateParentInterface;
import com.example.jeevan.hackforfood.dao.DBTransactions;
import com.example.jeevan.hackforfood.models.Order;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutCartActivity extends AppCompatActivity implements UpdateParentInterface {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.order_name)
    EditText txtOrderName;
    @BindView(R.id.txt_total)
    TextView txtTotalBill;
    @BindView(R.id.list_order_items)
    RecyclerView listOrderItems;

    OrderDetailsAdapter adapter;
    DBTransactions dbTransactions;
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
        dbTransactions = DBTransactions.getInstance(this);

        txtTotalBill.setText("\u20B9 " + order.getTotalCost());
        adapter = new OrderDetailsAdapter(this, order.getOrderItems());
        listOrderItems.setAdapter(adapter);
        listOrderItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.btn_order)
    public void saveOrder(View view) {
        order.setOccasion(AppContext.getInstance().getOccasion().getOccasion());
        order.setUser(AppContext.getInstance().getUser());
        String orderName = txtOrderName.getText().toString();
        if (orderName.isEmpty()) {
            orderName = order.getUser().getUserName() + " " + order.getOccasion();
        }
        order.setOrderName(orderName);
        dbTransactions.saveOrder(order);
        if (order.getId() != -1) {
            // TODO: show a order successful page
            Intent intent = new Intent(this, OccasionsActivity.class);
            // to remove all activities on stack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Couldn't save order! Please try again.", Toast.LENGTH_SHORT).show();
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
    public void update(String action, Bundle bundle) {
        if (action.equals(Constants.ACTION_INC_QTY)) {
            com.example.jeevan.hackforfood.models.MenuItem menuItem = bundle.getParcelable(Constants.MENU_ITEM);
            AppContext.getInstance().increaseQtyForItem(menuItem);
            txtTotalBill.setText(AppContext.getInstance().getOrder().getTotalCost()+"");
        } else if (action.equals(Constants.ACTION_DEC_QTY)) {
            long menuItemId = bundle.getLong(Constants.MENU_ITEM_ID, -1);
            AppContext.getInstance().decreaseQtyForItem(menuItemId);
            txtTotalBill.setText(AppContext.getInstance().getOrder().getTotalCost()+"");
        }
    }
}
