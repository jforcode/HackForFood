package com.example.jeevan.swiggy.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.adapters.SearchTermsAdapter;
import com.example.jeevan.swiggy.interfaces.SearchTermInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectSearchTermFragment extends DialogFragment {
    @BindView(R.id.list_search_terms)
    RecyclerView listSearchTerms;

    Context context;
    SearchTermInterface searchTermListener;

    public SelectSearchTermFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
        if (getActivity() instanceof SearchTermInterface) {
            this.searchTermListener = (SearchTermInterface) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_search_term, container, false);
        this.getDialog().setTitle("Select...");
        int width = context.getResources().getDimensionPixelSize(R.dimen.search_term_frag_width);
        int height = context.getResources().getDimensionPixelSize(R.dimen.search_term_frag_height);
        getDialog().getWindow().setLayout(width, height);
        ButterKnife.bind(this, view);
        SearchTermsAdapter adapter = new SearchTermsAdapter(context);
        listSearchTerms.setAdapter(adapter);
        listSearchTerms.setLayoutManager(new LinearLayoutManager(context));
        adapter.setSearchTerms(searchTermListener.getSearchTerms());
        return view;
    }
}
