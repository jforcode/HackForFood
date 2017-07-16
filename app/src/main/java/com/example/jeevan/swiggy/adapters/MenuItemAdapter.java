package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/16/17.
 */

public class MenuItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<MenuItem> items;
    int[] qty;

    public MenuItemAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        this.qty = new int[items.size()];
    }

    public void setItems(List<MenuItem> items) {
        if (items != null) {
            this.items.clear();
            this.items.addAll(items);
            this.qty = new int[items.size()];
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.li_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        MenuItem menuItem = items.get(position);
        final MenuItemViewHolder holder = (MenuItemViewHolder) holder1;
        holder.iconItem.setImageDrawable(Util.getNameDrawable(menuItem.getName()));
        holder.txtItemName.setText(menuItem.getName());
        holder.txtItemPrice.setText("\u20B9 " + menuItem.getPrice());
        qty[position] = (int) AppController.getInstance().getQty(menuItem.getId());
        if (qty[position] == 0) {
            holder.btnAddItemToOrder.setVisibility(View.VISIBLE);
            holder.btnPlusQty.setVisibility(View.GONE);
            holder.btnMinusQty.setVisibility(View.GONE);
            holder.txtQty.setVisibility(View.GONE);
        } else {
            holder.btnAddItemToOrder.setVisibility(View.GONE);
            holder.btnPlusQty.setVisibility(View.VISIBLE);
            holder.btnMinusQty.setVisibility(View.VISIBLE);
            holder.txtQty.setVisibility(View.VISIBLE);
            holder.txtQty.setText(qty[position]+"");
        }
        holder.btnPlusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty[position] == 0) {
                    holder.btnAddItemToOrder.setVisibility(View.GONE);
                    holder.btnPlusQty.setVisibility(View.VISIBLE);
                    holder.btnMinusQty.setVisibility(View.VISIBLE);
                    holder.txtQty.setVisibility(View.VISIBLE);
                }
                qty[position]++;
                holder.txtQty.setText(qty[position]+"");
                AppController.getInstance().addItem(items.get(position));
            }
        });
        holder.btnMinusQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty[position] == 1) {
                    holder.btnAddItemToOrder.setVisibility(View.VISIBLE);
                    holder.btnPlusQty.setVisibility(View.GONE);
                    holder.btnMinusQty.setVisibility(View.GONE);
                    holder.txtQty.setVisibility(View.GONE);
                }
                qty[position]--;
                holder.txtQty.setText(qty[position]+"");
                AppController.getInstance().minusItem(items.get(position));

            }
        });
        holder.btnAddItemToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty[position]++;
                holder.btnAddItemToOrder.setVisibility(View.GONE);
                holder.btnPlusQty.setVisibility(View.VISIBLE);
                holder.btnMinusQty.setVisibility(View.VISIBLE);
                holder.txtQty.setVisibility(View.VISIBLE);
                holder.txtQty.setText(qty[position]+"");
                AppController.getInstance().addItem(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_item)
        ImageView iconItem;
        @BindView(R.id.item_name)
        TextView txtItemName;
        @BindView(R.id.item_price)
        TextView txtItemPrice;
        @BindView(R.id.add_item)
        Button btnAddItemToOrder;
        @BindView(R.id.plus_qty)
        Button btnPlusQty;
        @BindView(R.id.minus_qty)
        Button btnMinusQty;
        @BindView(R.id.qty)
        TextView txtQty;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
