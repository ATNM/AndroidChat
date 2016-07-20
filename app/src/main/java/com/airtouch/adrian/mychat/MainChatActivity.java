package com.airtouch.adrian.mychat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import Util.API;

public class MainChatActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private ProgressBar mProgressBar;
    // private FirebaseListAdater

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.setOnClickListener(this);

        // check if the user is logged in
        if (!API.getInstance().isUserLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            // TODO: subscribe for notifications

            FragmentTransaction _transaction = getSupportFragmentManager().beginTransaction();
            _transaction.replace(R.id.home_container, ContactFragment.newInstance());
            _transaction.commit();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab){

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab){

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab){

    }

    @Override
    public void onClick(View v) {

    }
}
