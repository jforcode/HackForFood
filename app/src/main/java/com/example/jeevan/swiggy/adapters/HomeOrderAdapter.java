package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeevan.swiggy.models.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/30/17.
 */

public class HomeOrderAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<Order> listOrders;

    public HomeOrderAdapter(Context context) {
        this.context = context;
        this.listOrders = new ArrayList<>();
    }

    public void setListOrders(List<Order> listOrders) {
        if (listOrders != null) {
            this.listOrders = listOrders;
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HomeOrderViewHolder extends RecyclerView.ViewHolder {

        public HomeOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
