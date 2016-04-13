package com.lambroszannettos.themindmanifesto;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    MyFunctions functions = MyFunctions.getUniqueInstance();

    //For drawer menu functionality
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    //For headphone detection
    private HeadphoneStateReceiver myReceiver;

    static int currentLayoutId;

    //For media player
    static MediaPlayer mediaPlayer = MediaPlayerSingleton.getInstance().mediaPlayer;
    String skipSetting;
    int skipAmount;

    static String interventionCategory;

    public ArrayList<File> allAudioFiles;

    // Tracking
    protected Tracker mTracker;

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

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.enableAdvertisingIdCollection(true);
        // [END shared_tracker]

        //new instance of class to detect headphones plugged in/out
        myReceiver = new HeadphoneStateReceiver();

        //Get list of all m4a files in assets folder
        allAudioFiles = getAllFilesInAssetByExtension(this, AppConstant.INTERVENTION_FOLDER, ".m4a");

        try {
            skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
            skipAmount = Integer.parseInt(skipSetting);
        } catch (RuntimeException e) {
            Log.e("Wrong value entered", e.toString());
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {


                if (currentLayoutId != item.getItemId()) {


                    switch (item.getItemId()) {

                        case R.id.menu_home:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
                                    startActivity(loadPlayer);
                                }
                            }, 250);
                            break;

                        case R.id.menu_browse_all:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadBrowseAll = new Intent(getApplicationContext(), ChooseMeditation.class);
                                    interventionCategory = AppConstant.BROWSE_ALL;
                                    startActivity(loadBrowseAll);
                                }
                            }, 250);
                            break;

                        case R.id.menu_relationships:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadRelationships = new Intent(getApplicationContext(), ChooseMeditation.class);
                                    interventionCategory = AppConstant.RELATIONSHIPS;
                                    startActivity(loadRelationships);
                                }
                            }, 250);
                            break;

                        case R.id.menu_health:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadWork = new Intent(getApplicationContext(), ChooseMeditation.class);
                                    interventionCategory = AppConstant.HEALTH;
                                    startActivity(loadWork);
                                }
                            }, 250);
                            break;

                        case R.id.menu_business:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadGoals = new Intent(getApplicationContext(), ChooseMeditation.class);
                                    interventionCategory = AppConstant.BUSINESS;
                                    startActivity(loadGoals);
                                }
                            }, 250);
                            break;

                        case R.id.menu_about:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadAbout = new Intent(getApplicationContext(), About.class);
                                    startActivity(loadAbout);
                                }
                            }, 250);
                            break;

                        case R.id.menu_settings:

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent loadSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                                    startActivity(loadSettings);
                                }
                            }, 250);
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

    public ArrayList<File> getAllFilesInAssetByExtension(Context context, String subFolder, String extension) {
        Assert.assertNotNull(context);

        try {
            String[] files = context.getAssets().list(subFolder);
            ArrayList<File> filesWithExtension = new ArrayList<>();

            for (String file : files) {
                if (file.endsWith(extension)) {
                    filesWithExtension.add(new File(file));
                }
            }

            return filesWithExtension;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void selectIntervention(String title, String album, Boolean displayMessage) {

        AssetFileDescriptor afd;
        int index = 0;
        for (File f : allAudioFiles) {
            String tempAlbum = functions.getAudioAlbum(this, f, AppConstant.INTERVENTION_FOLDER);
            String tempTitle = functions.getAudioTitle(this, f, AppConstant.INTERVENTION_FOLDER);

            if (tempAlbum.equals(album) && tempTitle.equals(title)) {
                break;
            } else {
                index++;
            }
        }

        try {
            afd = this.getAssets().openFd(AppConstant.INTERVENTION_FOLDER + allAudioFiles.get(index).getAbsolutePath());

            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();

            functions.saveSetting(this, AppConstant.CURRENT_INTERVENTION, Integer.toString(index));

            // Log
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Interventions")
                    .setAction("Playing: " + getCurrentInterventionTitle())
                    .build());

            if (displayMessage) {
                Toast.makeText(BaseActivity.this, "Selected: " + getCurrentInterventionTitle(), Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentInterventionTitle() {
        String title;
        int index = Integer.parseInt(functions.readSetting(this, AppConstant.CURRENT_INTERVENTION));
        title = functions.getAudioTitle(this, allAudioFiles.get(index), AppConstant.INTERVENTION_FOLDER);

        return title;
    }

}