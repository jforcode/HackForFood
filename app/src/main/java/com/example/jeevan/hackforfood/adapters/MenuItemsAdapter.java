package com.example.jeevan.hackforfood.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.hackforfood.R;
import com.example.jeevan.hackforfood.activities.AppContext;
import com.example.jeevan.hackforfood.Util.Util;
import com.example.jeevan.hackforfood.interfaces.UpdateParentInterface;
import com.example.jeevan.hackforfood.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/16/17.
 */

public class MenuItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MenuItem> items;
    private long[] qty;
    private UpdateParentInterface parentUpdateListener;

    public MenuItemsAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        this.qty = new long[items.size()];
        if (context instanceof UpdateParentInterface) {
            parentUpdateListener = (UpdateParentInterface) context;
        }
        updateQtys();
    }

    public void setItems(List<MenuItem> items) {
        if (items != null) {
            this.items.clear();
            this.items.addAll(items);
            this.qty = new long[items.size()];
            updateQtys();
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.li_search_menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final MenuItem menuItem = items.get(position);
        final MenuItemViewHolder holder = (MenuItemViewHolder) holder1;
        holder.iconItem.setImageDrawable(Util.getNameDrawable(menuItem.getName()));
        holder.txtItemName.setText(menuItem.getName());
        holder.txtItemPrice.setText("\u20B9 " + menuItem.getPrice());
        // get the current qty for this item
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
                // increase qty
                if (qty[position] == 0) {
                    holder.btnAddItemToOrder.setVisibility(View.GONE);
                    holder.btnPlusQty.setVisibility(View.VISIBLE);
                    holder.btnMinusQty.setVisibility(View.VISIBLE);
                    holder.txtQty.setVisibility(View.VISIBLE);
                }
                qty[position]++;
                holder.txtQty.setText(qty[position]+"");
                AppContext.getInstance().increaseQtyForItem(menuItem);
                parentUpdateListener.update("", null);
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
                AppContext.getInstance().decreaseQtyForItem(menuItem.getId());
                parentUpdateListener.update("", null);

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
                AppContext.getInstance().increaseQtyForItem(items.get(position));
                parentUpdateListener.update("", null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateQtys() {
        for (int pos=0;pos<getItemCount();pos++) {
            MenuItem item = items.get(pos);
            long newQty = AppContext.getInstance().getCurrQtyForItem(item.getId());
            if (newQty != qty[pos]) {
                qty[pos] = newQty;
                notifyItemChanged(pos);
            }
        }
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
