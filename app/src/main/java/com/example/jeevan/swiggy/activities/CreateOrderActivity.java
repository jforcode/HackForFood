package com.example.jeevan.swiggy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.SearchTermInterface;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.adapters.RestaurantAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.fragments.SelectSearchTermFragment;
import com.example.jeevan.swiggy.models.Occasion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateOrderActivity extends AppCompatActivity implements SearchTermInterface {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pre_determined_terms)
    Button btnPreDetermined;
    @BindView(R.id.search_box)
    EditText txtSearch;
    @BindView(R.id.search_term)
    TextView txtCurrSearchTerm;
    @BindView(R.id.list_results)
    RecyclerView listSearchResults;

    DBTransactions transactions;
    RestaurantAdapter adapter;
    Occasion occasion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_order);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        occasion = AppController.getInstance().getOccasion();
        getSupportActionBar().setTitle(occasion.getOccasion());

        transactions = DBTransactions.getInstance(this);
        adapter = new RestaurantAdapter(this);
        listSearchResults.setAdapter(adapter);
        listSearchResults.setLayoutManager(new LinearLayoutManager(this));
        search("");
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    search(s.toString());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: onBackPressed();
                break;
        }
        return true;
    }

    static final String SEARCH_TERMS_FRAGMENT_TAG = "SEARCH_TERMS_FRAGMENT";

    @OnClick(R.id.pre_determined_terms)
    public void showPreDeterminedTermsList(View view) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment searchTermsFragment = manager.findFragmentByTag(SEARCH_TERMS_FRAGMENT_TAG);
        if (searchTermsFragment != null) {
            manager.beginTransaction().remove(searchTermsFragment).commit();
        }
        SelectSearchTermFragment searchFragment = new SelectSearchTermFragment();
        searchFragment.show(manager, SEARCH_TERMS_FRAGMENT_TAG);
    }


    @Override
    public void searchTerm(String searchTerm) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment searchTermsFragment = manager.findFragmentByTag(SEARCH_TERMS_FRAGMENT_TAG);
        if (searchTermsFragment != null) {
            manager.beginTransaction().remove(searchTermsFragment).commit();
        }
        search(searchTerm);
    }

    @Override
    public String[] getSearchTerms() {
        return occasion.getTerms();
    }

    private void search(String searchTerm) {
        if (searchTerm.isEmpty()) {
            adapter.setRestaurants(transactions.getAllRestaurants(occasion.getOccasion()));
            txtCurrSearchTerm.setText("Results for the occasion.");
        } else {
            adapter.setRestaurants(transactions.getAllRestaurants(searchTerm));
            txtCurrSearchTerm.setText("Related to \"" + searchTerm + "\"");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().getBottomTab().updateView();
    }
}
