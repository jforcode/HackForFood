package com.example.jeevan.swiggy.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;
import com.example.jeevan.swiggy.Util.Util;
import com.example.jeevan.swiggy.adapters.MenuItemsAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.interfaces.UpdateParentInterface;
import com.example.jeevan.swiggy.models.Occasion;
import com.example.jeevan.swiggy.models.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity implements UpdateParentInterface {
    @BindView(R.id.parent)
    CoordinatorLayout parent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rest_icon)
    ImageView imgRestIcon;
    @BindView(R.id.rest_name)
    TextView txtRestName;
    @BindView(R.id.rest_cuisines)
    TextView txtRestCuisines;
    @BindView(R.id.list_menu_items)
    RecyclerView listMenuItems;

    Restaurant restaurant;
    Occasion occasion;
    MenuItemsAdapter adapter;
    DBTransactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        restaurant = getIntent().getParcelableExtra(Constants.IP_RESTAURANT);
        occasion = AppContext.getInstance().getOccasion();
        transactions = DBTransactions.getInstance(this);

        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgRestIcon.setImageDrawable(Util.getNameDrawable(restaurant.getName()));
        txtRestName.setText(restaurant.getName());
        txtRestCuisines.setText(Util.getString(restaurant.getCuisines()));

        adapter = new MenuItemsAdapter(this);
        listMenuItems.setAdapter(adapter);
        listMenuItems.setLayoutManager(new LinearLayoutManager(this));

        adapter.setItems(transactions.getMenuItems(restaurant));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.showSummarySnackbar(this, parent);
        adapter.updateQtys();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void update(String action, Bundle bundle) {
        Util.showSummarySnackbar(this, parent);
    }
}
