package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OccasionsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occasions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.holder_chill, R.id.holder_hangout, R.id.holder_bday})
    public void showOccasions(View view) {
        Intent intent = new Intent(this, ChooseOrderActivity.class);
        switch (view.getId()) {
            case R.id.holder_chill:
                intent.putExtra(Constants.IP_OCCASION_NAME, "CHILLING");
                break;
            case R.id.holder_hangout:
                intent.putExtra(Constants.IP_OCCASION_NAME, "HANGOUT");
                break;
            case R.id.holder_bday:
                intent.putExtra(Constants.IP_OCCASION_NAME, "BDAY");
                break;
            default: intent.putExtra(Constants.IP_OCCASION_NAME, "NULL");
        }
        startActivity(intent);
    }
}
