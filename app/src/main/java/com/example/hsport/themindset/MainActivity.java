package com.example.hsport.themindset;

import android.app.Activity;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.os.Handler;

import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;
//import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    public android.os.Handler myHandler = new android.os.Handler();

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;


    MediaPlayer currentMeditation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //For the drawer toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //For the media player capabilities
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView txtTime = (TextView) findViewById(R.id.txt_time);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ocd);

        //The media player buttons
        final ImageButton playButton = (ImageButton) findViewById(R.id.btn_play_pause);
        final ImageButton stopButton = (ImageButton) findViewById(R.id.btn_stop);
        final ImageButton pauseButton = (ImageButton) findViewById(R.id.btn_pause);

        stopButton.setImageResource(R.drawable.btn_stop_disabled);
        pauseButton.setImageResource(R.drawable.btn_pause_disabled);

        seekBar.setMax(mediaPlayer.getDuration());

        final Runnable UpdateSongTime = new Runnable() {
            public void run() {
                double startTime = mediaPlayer.getCurrentPosition();
                txtTime.setText(String.format("%d min, %d sec",

                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                                toMinutes((long) startTime)))
                );
                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(this, 100);
            }
        };

        //PLAY button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.btn_play_disabled);
                    pauseButton.setImageResource(R.drawable.btn_pause);
                    stopButton.setImageResource(R.drawable.btn_stop);
                    myHandler.postDelayed(UpdateSongTime, 100);
                }
            }
        });


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


        //STOP button
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                playButton.setImageResource(R.drawable.btn_play);
                pauseButton.setImageResource(R.drawable.btn_pause_disabled);
                stopButton.setImageResource(R.drawable.btn_stop_disabled);
                mediaPlayer.reset();
                myHandler.postDelayed(UpdateSongTime, 100);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }


}
