package com.example.jeevan.swiggy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.interfaces.SearchTermInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jeevan on 7/16/17.
 */

public class SearchTermsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String[] searchTerms;
    SearchTermInterface searchTermSelectedListener;

    public SearchTermsAdapter(Context context) {
        this.context = context;
        if (context instanceof SearchTermInterface) {
            searchTermSelectedListener = (SearchTermInterface) context;
        }
        this.searchTerms = new String[]{};
    }

    public void setSearchTerms(String[] searchTerms) {
        if (searchTerms != null) {
            this.searchTerms = searchTerms;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchTermViewHolder(LayoutInflater.from(context).inflate(R.layout.li_search_term, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        SearchTermViewHolder holder1 = (SearchTermViewHolder) holder;
        holder1.txtSearchTerm.setText(searchTerms[position]);
        holder1.txtSearchTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTermSelectedListener.searchTerm(searchTerms[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchTerms.length;
    }

    public class SearchTermViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_term)
        TextView txtSearchTerm;

        public SearchTermViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
