package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Lambros on 26/03/16.
 */
public class SettingsActivity extends BaseActivity {

    MyFunctions functions = MyFunctions.getUniqueInstance();

    public SettingsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.settings_layout, contentFrameLayout);

        final SeekBar skipAmount = (SeekBar) findViewById(R.id.seekSkipAmount);
        final CheckBox showSplashScreen = (CheckBox) findViewById(R.id.checkboxSplashScreen);
        final TextView txtSkipAmount = (TextView) findViewById(R.id.txt_skip_amount);
        Button btnSaveSettings = (Button) findViewById(R.id.btn_save_settings);

        //Read settings
        final String skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
        String splashScreenSetting = functions.readSetting(this, AppConstant.SPLASH_SCREEN_KEY);

        skipAmount.setProgress(Integer.parseInt(skipSetting));
        showSplashScreen.setChecked(Boolean.parseBoolean(splashScreenSetting));

        skipAmount.setMax(AppConstant.MAX_SKIP_AMOUNT);
        txtSkipAmount.setText(Integer.toString(skipAmount.getProgress()));

        skipAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txtSkipAmount.setText(Integer.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                functions.saveSetting(getApplicationContext(),AppConstant.SKIP_KEY, Integer.toString(skipAmount.getProgress()));
                functions.saveSetting(getApplicationContext(),AppConstant.SPLASH_SCREEN_KEY, Boolean.toString(showSplashScreen.isChecked()));
            }
        });

    }
}
