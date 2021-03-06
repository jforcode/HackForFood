package com.example.jeevan.hackforfood.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.jeevan.hackforfood.R;
import com.example.jeevan.hackforfood.dao.DBTransactions;
import com.example.jeevan.hackforfood.models.User;

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
            valid = false;
            txtUserName.setError("Please enter a username!");
        }
        String pw = txtPassword.getText().toString();
        if (pw.isEmpty()) {
            valid = false;
            txtPassword.setError("Please enter a password!");
        }
        if (valid) {
            User user = DBTransactions.getInstance(this).validateAndGetUser(un, pw);
            if (user == null) {
                txtError.setVisibility(View.VISIBLE);
            } else {
                AppContext.getInstance().init(user);
                Intent intent = new Intent(this, OccasionsActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }
}
