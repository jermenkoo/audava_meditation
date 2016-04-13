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

        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.enableAdvertisingIdCollection(true);

        //new instance of class to detect headphones plugged in/out
        myReceiver = new HeadphoneStateReceiver();

        //Get list of all m4a files in assets folder
        allAudioFiles = getAllFilesInAssetByExtension(this, AppConstant.INTERVENTION_FOLDER, ".m4a");

        try {
            skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
            skipAmount  = Integer.parseInt(skipSetting);
        } catch (RuntimeException e) {
            Log.e("Wrong value entered", e.toString());
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final int currentId = item.getItemId();

                if (currentLayoutId != currentId) {
                    interventionCategory = AppConstant.categoryToString.get(currentId);

                    // launch the Intent we wanted
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Class newClass = AppConstant.choiceToScreen.get(currentId);
                            Intent newIntent = new Intent(getApplicationContext(), newClass);

                            startActivity(newIntent);
                        }
                    }, 300);

                    currentLayoutId = currentId;
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
            Log.d("Can't open file", e.toString());
        }
    }

    public String getCurrentInterventionTitle() {
        int index = Integer.parseInt(functions.readSetting(this, AppConstant.CURRENT_INTERVENTION));
        String title = functions.getAudioTitle(this, allAudioFiles.get(index), AppConstant.INTERVENTION_FOLDER);

        return title;
    }

}