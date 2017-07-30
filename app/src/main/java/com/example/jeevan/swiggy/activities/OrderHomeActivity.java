package com.example.jeevan.swiggy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jeevan.swiggy.R;

/*
 * this is where all bulk orders are showed in different sections like trending, saved etc.
 */
public class OrderHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home);
    }
}
