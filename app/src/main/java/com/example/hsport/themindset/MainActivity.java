package com.example.hsport.themindset;

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
//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;


    MediaPlayer currentMeditation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //For the media player capabilities

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ocd);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView txtTime = (TextView) findViewById(R.id.txt_time);

        seekBar.setMax(mediaPlayer.getDuration());
        txtTime.setText(Integer.toString(mediaPlayer.getDuration()));

        final ImageButton playButton = (ImageButton) findViewById(R.id.btn_play_pause);
        final ImageButton stopButton = (ImageButton) findViewById(R.id.btn_stop);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setImageResource(R.drawable.btn_play);
                }
                else {
                    mediaPlayer.start();
                    playButton.setImageResource(R.drawable.btn_pause);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    playButton.setImageResource(R.drawable.btn_play);
                }
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }




}
