package com.example.jeevan.swiggy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.adapters.OccasionAdapter;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Occasion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OccasionsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_occasions)
    RecyclerView listOccasions;

    OccasionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occasions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hi " + AppController.getInstance().getUser().getUserName());

        adapter = new OccasionAdapter(this);
        listOccasions.setAdapter(adapter);
        listOccasions.setLayoutManager(new LinearLayoutManager(this));

        loadOccasions();
    }

    private void loadOccasions() {
        List<Occasion> occasions = DBTransactions.getInstance(this).getAllOccasions();
        adapter.setOccasions(occasions);
    }
}
