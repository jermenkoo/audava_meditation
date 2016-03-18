package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        if(AppConstant.current_view == 0) {
            AppConstant.current_view = R.layout.activity_main;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, MainFragment.newInstance())
                .commit();
        drawerLayout.closeDrawers();
//
//        //This way a button (or other elements) can be added to a layout:
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_area);
//        Button button = new Button(this);
//        button.setText("Test");
//        linearLayout.addView(button);


//        For each menu item selected, the appropriate activity is started, which will add the layout to the
//        main_area LinearLayout in activity_main, and then the elements
//



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_relationships:
                        MenuItem thisItem = item;
//                        txtCurrentIntervention.setText("Finally!");
                        AppConstant.current_view = R.layout.fragment_relationships;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_content, MainFragment.newInstance())
                                .commit();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_home:

                        AppConstant.current_view = R.layout.player_layout;

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_content, MainFragment.newInstance())
                                .commit();
                        drawerLayout.closeDrawers();

                        Intent startPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
                        startActivity(startPlayer);

                        break;

                }
                return false;
            }
        });

    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}