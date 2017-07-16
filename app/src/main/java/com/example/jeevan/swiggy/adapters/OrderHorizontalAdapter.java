package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        OrderItem orderItem = orderItems.get(position);
        OrderElementViewHolder holder = (OrderElementViewHolder) holder1;
        holder.itemIcon.setImageDrawable(Util.getNameDrawable(orderItem.getMenuItem().getName()));
        holder.txtName.setText(orderItem.getMenuItem().getName());
        holder.txtQty.setText("Qty: " + orderItem.getQty());
        holder.txtPrice.setText("\u20B9 " + Util.getRoundedDouble(orderItem.getQty()*orderItem.getMenuItem().getPrice()));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    class OrderElementViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView itemIcon;
        @BindView(R.id.item_qty)
        TextView txtQty;
        @BindView(R.id.item_name)
        TextView txtName;
        @BindView(R.id.item_price)
        TextView txtPrice;

        public OrderElementViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
