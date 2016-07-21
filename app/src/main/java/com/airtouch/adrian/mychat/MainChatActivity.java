package com.airtouch.adrian.mychat;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseUser;

import Model.Model;
import Model.User;
import Util.API;

public class MainChatActivity extends Activity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

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


            // else, setup the user model
            FirebaseUser firebaseUser = API.getInstance().getCurrentUser();
            Model.user = new User(firebaseUser);

            // add the user to the database
            API.getInstance().addUserToDatabase(Model.user);

            // subscribe to notifications by using your user id as the topic
            API.getInstance().subscribeToTopic(Model.user.getId());

            //FragmentTransaction _transaction = getSupportFragmentManager().beginTransaction();
            //_transaction.replace(R.id.home_container, ContactFragment.newInstance());
            //_transaction.commit();
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
        if (tab.getPosition() == 0) {
            //getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                // unsubscribe from push on this account
                API.getInstance().unsubscribeFromTopic(Model.user.getId());

                // sign out
                API.getInstance().signOut();

                // invalidate the user model
                Model.user = new User();

                // start the login activity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
