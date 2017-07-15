package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/16/17.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Restaurant> restaurants;

    public RestaurantAdapter(Context context) {
        this.context = context;
        this.restaurants = new ArrayList<>();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        if (restaurants != null) {
            this.restaurants = restaurants;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.li_restaurants, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        RestaurantViewHolder holder = (RestaurantViewHolder) holder1;
        Restaurant restaurant = restaurants.get(position);
        holder.restIcon.setImageDrawable(Util.getNameDrawable(restaurant.getName()));
        holder.txtRestName.setText(restaurant.getName());
        StringBuilder cuisines = new StringBuilder();
        for (String cuisine : restaurant.getCuisines()) {
            cuisines.append(cuisine).append(", ");
        }
        cuisines.delete(cuisines.length()-2, cuisines.length());
        holder.txtRestCuisines.setText(cuisines);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.parent)
        RelativeLayout parent;
        @BindView(R.id.rest_icon)
        ImageView restIcon;
        @BindView(R.id.rest_name)
        TextView txtRestName;
        @BindView(R.id.rest_cuisines)
        TextView txtRestCuisines;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
