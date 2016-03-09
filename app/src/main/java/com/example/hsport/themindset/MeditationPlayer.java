package com.example.hsport.themindset;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lambros on 04/03/16.
 */
public class MeditationPlayer extends AppCompatActivity {

    public android.os.Handler myHandler = new android.os.Handler();

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //Find all the UI elements and assign variables to them
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final TextView txtTime = (TextView) findViewById(R.id.txt_time);
        final TextView txtCurrentDuration = (TextView) findViewById(R.id.txt_current_duration);
        final TextView txtCurrentIntervention = (TextView) findViewById(R.id.txt_current_intervention);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.flyeasy); //Load media file

        final ImageButton playButton = (ImageButton) findViewById(R.id.btn_play_pause);
        final ImageButton ffButton = (ImageButton) findViewById(R.id.btn_ff);
        final ImageButton rewButton = (ImageButton) findViewById(R.id.btn_rew);

//        final ImageButton stopButton = (ImageButton) findViewById(R.id.btn_stop);
//        final ImageButton pauseButton = (ImageButton) findViewById(R.id.btn_pause);

        //Initial settings for some UI elements
//        stopButton.setImageResource(R.drawable.btn_stop_disabled);
//        pauseButton.setImageResource(R.drawable.btn_pause_disabled);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setEnabled(false);
        txtCurrentDuration.setText(MyFunctions.returnTimeString(mediaPlayer.getDuration()));
        txtCurrentIntervention.setText("Fly Easy");

        final Runnable UpdateSongTime = new Runnable() {
            public void run() {
                double startTime = mediaPlayer.getCurrentPosition();
                txtTime.setText(MyFunctions.returnTimeString(startTime));
                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(this, 100);
            }
        };

        //PLAY button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mediaPlayer.isPlaying()) {
                    seekBar.setEnabled(true);
                    mediaPlayer.start();
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
//                    pauseButton.setImageResource(R.drawable.btn_pause);
//                    stopButton.setImageResource(R.drawable.btn_stop);
                    myHandler.postDelayed(UpdateSongTime, 100);
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setImageResource(android.R.drawable.ic_media_play);
//                    playButton.setImageResource(R.drawable.btn_play);
//                    stopButton.setImageResource(R.drawable.btn_stop);
                }
            }
        });

        ffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If adding 15 seconds is not going to go beyond length...
                if ((mediaPlayer.getCurrentPosition() + AppConstant.SKIP_AMOUNT)
                        <= mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + AppConstant.SKIP_AMOUNT);
                }
            }
        });

        rewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If going back 15 seconds is not going to go to less than 0...
                if ((mediaPlayer.getCurrentPosition() - AppConstant.SKIP_AMOUNT)
                        >= 0) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - AppConstant.SKIP_AMOUNT);
                } else {
                    mediaPlayer.seekTo(0);
                }
            }
        });

/*
        //PAUSE button
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    pauseButton.setImageResource(R.drawable.btn_pause_disabled);
                    playButton.setImageResource(R.drawable.btn_play);
                    stopButton.setImageResource(R.drawable.btn_stop);
                }
            }
        });
*/
/*
        //STOP button
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
                seekBar.setEnabled(false);
                playButton.setImageResource(R.drawable.btn_play);
                pauseButton.setImageResource(R.drawable.btn_pause_disabled);
                stopButton.setImageResource(R.drawable.btn_stop_disabled);
                myHandler.postDelayed(UpdateSongTime, 100);
            }
        });*/

        //Track seekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Add functionality to move the media player to the
                //chosen place
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }


}
