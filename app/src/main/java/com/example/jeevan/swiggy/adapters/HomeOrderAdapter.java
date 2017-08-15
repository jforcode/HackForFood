package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.jeevan.swiggy.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/30/17.
 */

public class HomeOrderAdapter extends RecyclerView.Adapter<ViewHolder> implements PopupMenu.OnMenuItemClickListener {
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
        holder.orderIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.order_3));
        holder.txtOrderName.setText(curr.getOrderName());
        holder.txtOrderPrice.setText(Util.getRoundedDouble(curr.getTotalCost())+"");
        holder.userIcon.setImageDrawable(Util.getNameDrawableRound(curr.getUser().getUserName()));
        StringBuffer orderItems = new StringBuffer();
        for (OrderItem item : curr.getOrderItems()) {
            orderItems.append(item.getMenuItem().getName() + ", ");
        }
        if (orderItems.length() > 0) {
            orderItems.deleteCharAt(orderItems.length()-1);
        }
        holder.txtOrderItems.setText(orderItems);
        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsMenu(v);
            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void showOptionsMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_order_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // todo: handle menu clicks
        return true;
    }

    public class HomeOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.li_order_parent)
        CardView parent;
        @BindView(R.id.li_order_icon)
        ImageView orderIcon;
        @BindView(R.id.li_order_user_icon)
        ImageView userIcon;
        @BindView(R.id.li_order_name)
        TextView txtOrderName;
        @BindView(R.id.li_order_items)
        TextView txtOrderItems;
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
