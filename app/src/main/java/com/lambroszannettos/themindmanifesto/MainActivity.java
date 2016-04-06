package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Read settings if they exist:
        MyFunctions functions = MyFunctions.getUniqueInstance();
        String skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
        String splashScreenSetting = functions.readSetting(this, AppConstant.SPLASH_SCREEN_KEY);
        String lastIntervention = functions.readSetting(this, AppConstant.CURRENT_INTERVENTION);

        //If they are null, write the settings with the default values
        if(skipSetting == "") {
            functions.saveSetting(this, AppConstant.SKIP_KEY, Integer.toString(AppConstant.DEFAULT_SKIP_AMOUNT));
        }
        if(splashScreenSetting == "") {
            functions.saveSetting(this, AppConstant.SPLASH_SCREEN_KEY, Boolean.toString(AppConstant.DEFAULT_SPLASH_SCREEN_SETTING));
        }
        if(lastIntervention == "") {
            //Set file at index 0 as last intervention if there was none saved
            functions.saveSetting(this, AppConstant.CURRENT_INTERVENTION, "0");
        }

        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        selectIntervention(Integer.parseInt(functions.readSetting(this, AppConstant.CURRENT_INTERVENTION)), false);


        //...then load the default screen which is the player
        Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
        startActivity(loadPlayer);

//        currentLayoutId = R.id.menu_home;
    }


}
