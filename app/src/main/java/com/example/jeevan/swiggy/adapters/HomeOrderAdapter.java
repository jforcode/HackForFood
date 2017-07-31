package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.activities.OrderDetailsActivity;
import com.example.jeevan.swiggy.models.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
        return new HomeOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.li_order_home, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder1, int position) {
        HomeOrderViewHolder holder = (HomeOrderViewHolder) holder1;
        Order curr = listOrders.get(position);
        holder.orderIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.occasion_chilling));
        holder.txtOrderName.setText(curr.getOrderName());
        holder.txtUserName.setText(curr.getUser().getUserName());
        holder.txtOrderPrice.setText(Util.getRoundedDouble(curr.getTotalCost())+"");
        // TODO: show menu on options icon click, to add the order to cart
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class HomeOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.li_order_parent)
        RelativeLayout parent;
        @BindView(R.id.li_order_icon)
        ImageView orderIcon;
        @BindView(R.id.li_order_name)
        TextView txtOrderName;
        @BindView(R.id.li_user_name)
        TextView txtUserName;
        @BindView(R.id.li_order_total_price)
        TextView txtOrderPrice;
        @BindView(R.id.li_btn_options)
        Button btnMenu;

        public HomeOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
