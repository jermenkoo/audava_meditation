package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.SeekBar;

/**
 * Created by Lambros on 26/03/16.
 */
public class SettingsActivity extends BaseActivity {

    public SettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.settings_layout, contentFrameLayout);

        SeekBar skipAmount = (SeekBar) findViewById(R.id.seekSkipAmount);

        skipAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
