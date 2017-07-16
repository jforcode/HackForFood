package com.example.jeevan.swiggy.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.models.Order;

/**
 * Created by jeevan on 7/16/17.
 */

public class BottomTabController {
    View bottomTabView;
    TextView txtNoItems;
    TextView txtTotalCost;
    Button btnGoToCart;
    Context context;

    public BottomTabController(Context context) {
        this.context = context;
    }

    public void setBottomTabView(View view) {
        if (view == null) return;
        this.bottomTabView = view;
        this.txtNoItems = (TextView) bottomTabView.findViewById(R.id.num_items);
        this.txtTotalCost = (TextView) bottomTabView.findViewById(R.id.total_price);
        this.btnGoToCart = (Button) bottomTabView.findViewById(R.id.btn_view_cart);

        this.btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CheckoutCartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        updateView();
    }

    public void updateView() {
        Order currentOrder = AppController.getInstance().getOrder();
        if (bottomTabView == null) return;
        if (currentOrder != null && currentOrder.getQty() != 0) {
            bottomTabView.setVisibility(View.VISIBLE);
            txtNoItems.setText(currentOrder.getQty() + "");
            txtTotalCost.setText("\u20B9 " + currentOrder.getTotalCost());
        } else {
            bottomTabView.setVisibility(View.GONE);
        }
    }
}
