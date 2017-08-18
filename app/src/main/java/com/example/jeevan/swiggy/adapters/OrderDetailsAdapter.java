package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.activities.AppContext;
import com.example.jeevan.swiggy.interfaces.UpdateParentInterface;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.models.OrderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
        restaurantPosMap = new TreeSet<>();
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        if (holder1 instanceof RestaurantViewHolder) {
            // restaurant header
            RestaurantViewHolder holder = (RestaurantViewHolder) holder1;
            String restName = (String) objects.get(position);
            holder.txtRestName.setText(restName);
            holder.restIcon.setImageDrawable(Util.getNameDrawableRound(restName));

        } else if (holder1 instanceof MenuItemViewHolder) {
            // items
            final MenuItemViewHolder holder = (MenuItemViewHolder) holder1;
            // since this orderItem is the same reference as in the global order item,
            // don't delete qty from here, updating qty from parent is fine
            final OrderItem orderItem = (OrderItem) objects.get(position);
            holder.txtItemName.setText(orderItem.getMenuItem().getName());
            holder.txtItemPrice.setText(orderItem.getMenuItem().getPrice()+"");
            holder.txtQty.setText(orderItem.getQty()+"");
            holder.btnAddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // increase the qty
                    if (parentListener != null) {
                        Bundle data = new Bundle();
                        data.putParcelable(Constants.MENU_ITEM, orderItem.getMenuItem());
                        parentListener.update(Constants.ACTION_INC_QTY, data);
                        holder.txtQty.setText(orderItem.getQty()+"");
                    }
                }
            });
            holder.btnMinusQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // decrease qty
                    // if only one item, confirm removal before deleting
                    if (orderItem.getQty() == 1) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context)
                                .setTitle("Are you sure you want to delete this item?")
                                .setCancelable(true)
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (parentListener != null) {
                                            Bundle data = new Bundle();
                                            data.putLong(Constants.MENU_ITEM_ID, orderItem.getMenuItem().getId());
                                            parentListener.update(Constants.ACTION_DEC_QTY, data);
                                            deleteItemAtPosition(position);
                                        }
                                    }
                                })
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                });
                        mBuilder.show();
                    } else {
                        orderItem.setQty(orderItem.getQty() - 1);
                        holder.txtQty.setText(orderItem.getQty()+"");
                        if (parentListener != null) {
                            Bundle data = new Bundle();
                            data.putLong(Constants.MENU_ITEM_ID, orderItem.getMenuItem().getId());
                            parentListener.update(Constants.ACTION_DEC_QTY, data);
                        }
                    }
                }
            });
        }
    }

    private void deleteItemAtPosition(int position) {
        if (position == 0) {
            // this call should not happen
            // because first should always be a restaurant header, and this method is deleting items
            // still, don't delete this condition
        } else if (position == objects.size()-1) {
            if (objects.size() > 1 && objects.get(position-1) instanceof String) {
                // only item for the restaurant, delete restaurant also
                deleteSingleItem(position);
                notifyItemRangeRemoved(position-1, 2);
                notifyItemRangeChanged(position-1, 2);
            } else {
                objects.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position);
            }
        } else {
            // size is atleast 3
            int prevCount = objects.size();
            boolean restDeleted = false;
            if (objects.get(position-1) instanceof String && objects.get(position+1) instanceof String) {
                // only item for restaurant, delete restaurant also
                deleteSingleItem(position);
                notifyItemRangeRemoved(position-1, 2);
                restDeleted = true;
            } else {
                objects.remove(position);
                notifyItemRemoved(position);
            }
            // all restaurants after position have moved up by 1 or 2
            List<Integer> toUpdatePosList = new ArrayList<>();
            for (int restPos : restaurantPosMap) {
                if (restPos > position) toUpdatePosList.add(restPos);
            }
            for (int pos : toUpdatePosList) {
                restaurantPosMap.remove(pos);
                if (restDeleted) restaurantPosMap.add(pos - 2);
                else restaurantPosMap.add(pos - 1);
            }
            if (restDeleted) {
                notifyItemRangeChanged(position - 1, prevCount - position + 1);
            } else {
                notifyItemRangeChanged(position, prevCount - position);
            }
        }
    }

    /**
     * deletes single item, i.e. an item which was the only one in its restaurant
     * @param position position of the item to remove
     */
    private void deleteSingleItem(int position) {
        objects.remove(position);
        objects.remove(position-1);
        restaurantPosMap.remove(position-1);
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

