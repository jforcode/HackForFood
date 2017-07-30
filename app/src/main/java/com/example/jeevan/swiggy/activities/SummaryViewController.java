package com.example.jeevan.swiggy.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jeevan on 7/30/17.
 * this class manages the view of the bottom tab showed for the order summary
 */

public class SummaryViewController {
    Context context;
    View summaryView;
    @BindView(R.id.num_items)
    TextView txtTotalQty;
    @BindView(R.id.total_price)
    TextView txtTotalPrice;

    public SummaryViewController(Context context, View summaryView) {
        this.summaryView = summaryView;
        this.context = context;
        ButterKnife.bind(this, summaryView);
        this.summaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart();
            }
        });
    }

    public void updateView(long qty, double price) {
        txtTotalQty.setText(qty+"");
        txtTotalPrice.setText("\u02b9 " + Util.getRoundedDouble(price));
    }

    private void showCart() {
        Intent intent = new Intent(context, CheckoutCartActivity.class);
        context.startActivity(intent);
    }
}
