package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        final FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_home:
                        Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
                        startActivity(loadPlayer);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_relationships:
                        Intent loadRelationships = new Intent(getApplicationContext(), ChooseMeditation.class);
                        startActivity(loadRelationships);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.menu_about:
                        Intent loadAbout = new Intent(getApplicationContext(), About.class);
                        startActivity(loadAbout);
                        drawerLayout.closeDrawers();
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

}