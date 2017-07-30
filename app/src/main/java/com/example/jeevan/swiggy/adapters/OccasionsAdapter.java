package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.activities.AppContext;
import com.example.jeevan.swiggy.activities.OrderHomeActivity;
import com.example.jeevan.swiggy.models.Occasion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/15/17.
 */

public class OccasionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Occasion> occasions;

    public OccasionsAdapter(Context context) {
        this.context = context;
        this.occasions = new ArrayList<>();
    }

    public void setOccasions(List<Occasion> occasions) {
        if (occasions != null) {
            this.occasions = occasions;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.li_occasion, parent, false);
        return new OcassionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        OcassionViewHolder holder = (OcassionViewHolder) holder1;
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderHomeActivity.class);
                AppContext.getInstance().setOccasion(occasions.get(position));
                context.startActivity(intent);
            }
        });
        holder.ocassionIcon.setImageResource(occasions.get(position).getDrawable());
        holder.txtName.setText(occasions.get(position).getOccasion());
        holder.txtDesc.setText(occasions.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return occasions.size();
    }

    public class OcassionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.holder)
        RelativeLayout parent;
        @BindView(R.id.occasion_icon)
        ImageView ocassionIcon;
        @BindView(R.id.occasion_name)
        TextView txtName;
        @BindView(R.id.occasion_desc)
        TextView txtDesc;

        public OcassionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
