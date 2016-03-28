package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    //For drawer menu functionality
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    //For headphone detection
    private HeadphoneStateReceiver myReceiver;

    static int currentLayoutId;

    private MyFunctions myFunctions = MyFunctions.getUniqueInstance();

    //For media player
    private static final MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
    public static MediaPlayer mediaPlayer = mediaPlayerSingleton.getMediaPlayerInstance();

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

        //new instance of class to detect headphones plugged in/out
        myReceiver = new HeadphoneStateReceiver();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (currentLayoutId != item.getItemId()) {
                    switch (item.getItemId()) {

                        case R.id.menu_home:
                            Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
                            startActivity(loadPlayer);
                            break;

                        case R.id.menu_browse_all:
                            break;

                        case R.id.menu_relationships:
                            Intent loadRelationships = new Intent(getApplicationContext(), ChooseMeditation.class);
                            startActivity(loadRelationships);
                            break;

                        case R.id.menu_work:
                            break;

                        case R.id.menu_goals:
                            break;

                        case R.id.menu_about:
                            Intent loadAbout = new Intent(getApplicationContext(), About.class);
                            startActivity(loadAbout);
                            break;

                        case R.id.menu_settings:
                            Intent loadSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                            startActivity(loadSettings);
                            break;
                    }

                    currentLayoutId = item.getItemId();
                }

                drawerLayout.closeDrawers();
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
    protected void onResume() {
        //Register receiver for headphone detection
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //Unregister receiver for headphone detection
        unregisterReceiver(myReceiver);
        super.onPause();
    }
}