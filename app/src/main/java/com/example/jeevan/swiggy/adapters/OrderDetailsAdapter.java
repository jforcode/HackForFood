package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.interfaces.UpdateParentInterface;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.models.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TODO: maybe combine this with the MenuItemsAdapter
 * Created by jeevan on 7/16/17.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int VIEW_TYPE_RESTAURANT = 1, VIEW_TYPE_MENU_ITEM = 2;
    Context context;
    UpdateParentInterface parentListener;
    List<Object> objects;
    Set<Integer> restaurantPosMap;

    public OrderDetailsAdapter(Context context, List<OrderItem> listOrderItems) {
        this.context = context;
        if (context instanceof UpdateParentInterface) {
            parentListener = (UpdateParentInterface) context;
        }
        if (listOrderItems == null) listOrderItems = new ArrayList<>();
        createLists(listOrderItems);
    }

    private void createLists(List<OrderItem> listOrderItems) {
        Collections.sort(listOrderItems, new Comparator<OrderItem>() {
            @Override
            public int compare(OrderItem o1, OrderItem o2) {
                return o1.getMenuItem().getRestaurant().getName().compareTo(o2.getMenuItem().getRestaurant().getName());
            }
        });
        objects = new ArrayList<>();
        restaurantPosMap = new HashSet<>();
        String prev = null;
        for (int i=0;i<listOrderItems.size();i++) {
            String curr = listOrderItems.get(i).getMenuItem().getRestaurant().getName();
            if (!curr.equalsIgnoreCase(prev)) {
                objects.add(curr);
                restaurantPosMap.add(objects.size()-1);
            }
            objects.add(listOrderItems.get(i));
            prev = curr;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (restaurantPosMap.contains(position)) {
            return VIEW_TYPE_RESTAURANT;
        } else {
            return VIEW_TYPE_MENU_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RESTAURANT) {
            return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.li_checkout_rest, parent, false));
        } else {
            return new MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.li_checkout_item, parent, false));
        }
    }

    private void updatePosMap() {
        restaurantPosMap.clear();
        for (int i=0;i<objects.size();i++) {
            if (objects.get(i) instanceof String) {
                restaurantPosMap.add(i);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        if (holder1 instanceof RestaurantViewHolder) {
            RestaurantViewHolder holder = (RestaurantViewHolder) holder1;
            String restName = (String) objects.get(position);
            holder.txtRestName.setText(restName);
            holder.restIcon.setImageDrawable(Util.getNameDrawableRound(restName));
        } else if (holder1 instanceof MenuItemViewHolder) {
            final MenuItemViewHolder holder = (MenuItemViewHolder) holder1;
            final OrderItem orderItem = (OrderItem) objects.get(position);
            holder.txtItemName.setText(orderItem.getMenuItem().getName());
            holder.txtItemPrice.setText("\u20B9 " + orderItem.getMenuItem().getPrice());
            holder.txtQty.setText(orderItem.getQty()+"");
            holder.btnAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long prevQty = orderItem.getQty();
                    orderItem.setQty(orderItem.getQty() + 1);
                    holder.txtQty.setText(orderItem.getQty()+"");
                    Bundle data = new Bundle();
                    data.putParcelable(Constants.IP_ORDER_ITEM, orderItem);
                    data.putLong(Constants.IP_ORDER_PREV_QTY, prevQty);
                    parentListener.update(data);
                }
            });
            holder.btnMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long prevQty = orderItem.getQty();
                    Bundle data = new Bundle();
                    data.putParcelable(Constants.IP_ORDER_ITEM, orderItem);
                    data.putLong(Constants.IP_ORDER_PREV_QTY, prevQty);
                    if (orderItem.getQty() == 1) {
                        // delete this
                        orderItem.setQty(0);
                        holder.txtQty.setText("0");
                        if (objects.get(position-1) instanceof String) {
                            if (position == getItemCount()-1 || objects.get(position+1) instanceof String) {
                                objects.remove(position);
                                objects.remove(position-1);
                            } else {
                                objects.remove(position);
                            }
                        } else {
                            objects.remove(position);
                        }
                        parentListener.update(data);
                        updatePosMap();
                        notifyDataSetChanged();
                    } else {
                        orderItem.setQty(orderItem.getQty() - 1);
                        holder.txtQty.setText(orderItem.getQty()+"");
                        parentListener.update(data);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rest_name)
        TextView txtRestName;
        @BindView(R.id.rest_icon)
        ImageView restIcon;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView txtItemName;
        @BindView(R.id.item_price)
        TextView txtItemPrice;
        @BindView(R.id.plus_qty)
        Button btnAddQty;
        @BindView(R.id.qty)
        TextView txtQty;
        @BindView(R.id.minus_qty)
        Button btnMinusQty;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

