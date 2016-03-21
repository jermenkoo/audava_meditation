package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Lambros on 04/03/16.
 */
public class MeditationPlayer extends BaseActivity {

    //Not sure if this handler is needed anymore,
    //another handler is being used to handle the songTime updating

    static Handler handler = new Handler() {
        public void handleMessage(Message m) {
//            Intent startMeditationPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
//            startActivity(startMeditationPlayer);
        }
    };


    boolean isSeekBarTracking = false;
    public android.os.Handler myHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Inflate the appropriate layout within the content_frame FrameLayout
        //which is in the activity_main layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.player_layout, contentFrameLayout);

        //Find all the UI elements and assign variables to them
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final TextView txtTime = (TextView) findViewById(R.id.txt_time);
        final TextView txtCurrentDuration = (TextView) findViewById(R.id.txt_current_duration);
        final TextView txtCurrentIntervention = (TextView) findViewById(R.id.txt_current_intervention);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.flyeasy); //Load media file

        final ImageButton playButton = (ImageButton) findViewById(R.id.btn_play_pause);
        final ImageButton ffButton = (ImageButton) findViewById(R.id.btn_ff);
        final ImageButton rewButton = (ImageButton) findViewById(R.id.btn_rew);

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setEnabled(false);
        txtCurrentDuration.setText(MyFunctions.returnTimeString(mediaPlayer.getDuration()));
        txtCurrentIntervention.setText("Fly Easy");

        final Runnable UpdateSongTime = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration() - 2000) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.pause();
                    playButton.setImageResource(android.R.drawable.ic_media_play);
                }

                double startTime = mediaPlayer.getCurrentPosition();
                txtTime.setText(MyFunctions.returnTimeString(startTime));
                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(this, 999);
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
                    // updates the current song time
                    myHandler.postDelayed(UpdateSongTime, 1000);
                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playButton.setImageResource(android.R.drawable.ic_media_play);
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

        //Track seekBar changes
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int secProgress = seekBar.getSecondaryProgress();
                    if (secProgress > progress || isSeekBarTracking) {
                        mediaPlayer.seekTo(progress);
                    } else {
                        seekBar.setProgress(seekBar.getProgress());
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBarTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBarTracking = false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress((seekBar.getMax() / 100) * percent);
            }
        });

        Thread song_player = new Thread() {
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        };

        song_player.start();

    }

}
