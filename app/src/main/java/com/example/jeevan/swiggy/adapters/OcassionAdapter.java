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
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.activities.ChooseOrderActivity;
import com.example.jeevan.swiggy.models.Ocassion;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/15/17.
 */

public class OcassionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Ocassion> ocassions;

    public OcassionAdapter(Context context) {
        this.context = context;
        this.ocassions = new ArrayList<>();
        ocassions.add(new Ocassion("Hangout", R.drawable.occasion_hangout, "Wanna hangout! Order some snacks..."));
        ocassions.add(new Ocassion("Regular", R.drawable.occasion_bday, "Order the regulars!"));
        ocassions.add(new Ocassion("Surprise Me", R.drawable.occasion_chilling, "Want a break from everyday. Get surprised!"));

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
                Intent intent = new Intent(context, ChooseOrderActivity.class);
                String occasion = ocassions.get(position).getOcassion();
                AppController.getInstance().changeOccasion(occasion);
                intent.putExtra(Constants.IP_OCCASION_NAME, occasion);
                context.startActivity(intent);
            }
        });
        holder.ocassionIcon.setImageResource(ocassions.get(position).getDrawable());
        holder.txtDesc.setText(ocassions.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return ocassions.size();
    }

    public class OcassionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.holder)
        RelativeLayout parent;
        @BindView(R.id.occasion_icon)
        ImageView ocassionIcon;
        @BindView(R.id.occasion_desc)
        TextView txtDesc;

        public OcassionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
