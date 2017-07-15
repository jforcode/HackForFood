package com.example.jeevan.swiggy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeevan.swiggy.R;
import com.example.jeevan.swiggy.Util.AppController;
import com.example.jeevan.swiggy.dao.DBTransactions;
import com.example.jeevan.swiggy.models.Ocassion;
import com.example.jeevan.swiggy.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.username)
    TextView txtUserName;
    @BindView(R.id.password)
    TextView txtPassword;
    @BindView(R.id.error)
    TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.login)
    public void login(View view) {
        boolean valid = true;
        String un = txtUserName.getText().toString();
        if (un.isEmpty()) {
            txtUserName.setError("Please enter a username!");
        }
        String pw = txtPassword.getText().toString();
        if (pw.isEmpty()) {
            txtPassword.setError("Please enter a password!");
        }
        if (valid) {
            User user = DBTransactions.getInstance(this).validateAndGetUser(un, pw);
            if (user == null) {
                txtError.setVisibility(View.VISIBLE);
            } else {
                AppController.getInstance().setUser(user);
                Intent intent = new Intent(this, OccasionsActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }
}
