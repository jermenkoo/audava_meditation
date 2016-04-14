package com.lambroszannettos.themindmanifesto;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by Lambros on 04/03/16.
 */
public class MeditationPlayer extends BaseActivity {

    boolean isSeekBarTracking = false;
    public android.os.Handler myHandler = new android.os.Handler();

    private TextView txtTime;
    private TextView txtCurrentDuration;
    private TextView txtCurrentIntervention;
    private TextView txtHeadphoneMessage;
    private ImageButton playButton;
    private ImageButton ffButton;
    private ImageButton rewButton;
    private SeekArc seekArc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inflate the appropriate layout within the content_frame FrameLayout
        //which is in the activity_main layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.player_layout, contentFrameLayout);

        //Get UI elements and assign variables to them
        txtTime                 = (TextView) findViewById(R.id.txt_time);
        txtCurrentDuration      = (TextView) findViewById(R.id.txt_current_duration);
        txtCurrentIntervention  = (TextView) findViewById(R.id.txt_current_intervention);
        txtHeadphoneMessage     = (TextView) findViewById(R.id.txt_headphone_message);
        playButton              = (ImageButton) findViewById(R.id.btn_play_pause);
        ffButton                = (ImageButton) findViewById(R.id.btn_ff);
        rewButton               = (ImageButton) findViewById(R.id.btn_rew);
        seekArc                 = (SeekArc) findViewById(R.id.seekArc);

        if (mediaPlayer.isPlaying()) {
            playButton.setImageResource(android.R.drawable.ic_media_pause);
        } else {
            playButton.setImageResource(android.R.drawable.ic_media_play);
        }

        txtCurrentDuration.setText(MyFunctions.returnTimeString(mediaPlayer.getDuration()));
        txtCurrentIntervention.setText(getCurrentInterventionTitle());

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
                seekArc.setMax(mediaPlayer.getDuration());

                seekArc.setProgress((int) startTime);
                seekArc.invalidate();

                myHandler.postDelayed(this, 999);

                //For headphone message, create unique Runnable for it later
                if (!AppConstant.HEADSET_ON) {
                    txtHeadphoneMessage.setText("For the best experience,\nplease plug your headset in.");
                } else {
                    txtHeadphoneMessage.setText("");
                }
            }
        };

        UpdateSongTime.run();
        myHandler.postDelayed(UpdateSongTime, 999);

        //PLAY button
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    seekArc.setEnabled(true);
                    mediaPlayer.start();
                    playButton.setImageResource(android.R.drawable.ic_media_pause);
                    // updates the current song time
                    myHandler.postDelayed(UpdateSongTime, 999);
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
                if ((mediaPlayer.getCurrentPosition() + skipAmount)
                        <= mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + skipAmount);
                }
            }
        });

        rewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If going back 15 seconds is not going to go to less than 0...
                if ((mediaPlayer.getCurrentPosition() - skipAmount)
                        >= 0) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - skipAmount);

                } else {
                    mediaPlayer.seekTo(0);
                }
            }
        });


        //Track seekBar changes
        seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
                if (fromUser) {
                    int secProgress = seekArc.getSweepAngle();
                    UpdateSongTime.run();
                    if (secProgress > progress || isSeekBarTracking) {
                        mediaPlayer.seekTo(progress);
                    } else {
                        seekArc.setProgress(seekArc.getProgress());
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
                isSeekBarTracking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                isSeekBarTracking = false;
            }
        });


        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekArc.setProgress((seekArc.getMax() / 100) * percent);
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}
