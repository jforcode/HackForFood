package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/15/17.
 */

public class OrderHorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<OrderItem> orderItems;
    Context context;

    public OrderHorizontalAdapter(Context context) {
        this.context = context;
        this.orderItems = new ArrayList<>();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        if (orderItems != null) {
            this.orderItems = orderItems;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.li_horizontal_order, parent, false);
        return new OrderElementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    class OrderElementViewHolder extends RecyclerView.ViewHolder {

        public OrderElementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
