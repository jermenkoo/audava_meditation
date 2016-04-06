package com.lambroszannettos.themindmanifesto;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lambros on 04/03/16.
 */
public class MeditationPlayer extends BaseActivity {

    boolean isSeekBarTracking = false;
    public android.os.Handler myHandler = new android.os.Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the appropriate layout within the content_frame FrameLayout
        //which is in the activity_main layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.player_layout, contentFrameLayout);

        MyFunctions functions = MyFunctions.getUniqueInstance();

        //Find all the UI elements and assign variables to them
        final TextView txtTime = (TextView) findViewById(R.id.txt_time);
        final TextView txtCurrentDuration = (TextView) findViewById(R.id.txt_current_duration);
        final TextView txtCurrentIntervention = (TextView) findViewById(R.id.txt_current_intervention);
        final TextView txtHeadphoneMessage = (TextView) findViewById(R.id.txt_headphone_message);
        final ImageButton playButton = (ImageButton) findViewById(R.id.btn_play_pause);
        final ImageButton ffButton = (ImageButton) findViewById(R.id.btn_ff);
        final ImageButton rewButton = (ImageButton) findViewById(R.id.btn_rew);
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        final Runnable UpdateSongTime = new Runnable() {
            @Override
            public void run() {

                    if (mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration() - AppConstant.SAFE_ENDING) {
                        mediaPlayer.seekTo(0);
                        mediaPlayer.pause();
                        playButton.setImageResource(android.R.drawable.ic_media_play);
                    }

                    double startTime = mediaPlayer.getCurrentPosition();
                    txtTime.setText(MyFunctions.returnTimeString(startTime));
                seekBar.setMax(mediaPlayer.getDuration());

                seekBar.setProgress((int) startTime);
                    myHandler.postDelayed(this, 999);

                //For headphone message, create unique Runnable for it later
                if (AppConstant.HEADSET_ON == false) {
                    txtHeadphoneMessage.setText("For the best experience,\nplease plug your headset in.");
                } else {
                    txtHeadphoneMessage.setText("");
                }
            }
        };


        //If mediaPlayer was not loaded earlier, load it now
//            seekBar.setMax(mediaPlayer.getDuration());
//            seekBar.setProgress(mediaPlayer.getCurrentPosition());
//        Toast.makeText(MeditationPlayer.this, "Cur Pos:" + mediaPlayer.getCurrentPosition(), Toast.LENGTH_SHORT).show();

            if (mediaPlayer.isPlaying()) {
                playButton.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                playButton.setImageResource(android.R.drawable.ic_media_play);
            }

        UpdateSongTime.run();
        myHandler.postDelayed(UpdateSongTime, 999);

//        seekBar.setMax(mediaPlayer.getDuration());
        txtCurrentDuration.setText(MyFunctions.returnTimeString(mediaPlayer.getDuration()));
        txtCurrentIntervention.setText(getCurrentInterventionTitle());


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
                if ((mediaPlayer.getCurrentPosition() + AppConstant.DEFAULT_SKIP_AMOUNT)
                        <= mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + AppConstant.DEFAULT_SKIP_AMOUNT);
                }
            }
        });

        rewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If going back 15 seconds is not going to go to less than 0...
                if ((mediaPlayer.getCurrentPosition() - AppConstant.DEFAULT_SKIP_AMOUNT)
                        >= 0) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - AppConstant.DEFAULT_SKIP_AMOUNT);

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
                    UpdateSongTime.run();
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

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}
