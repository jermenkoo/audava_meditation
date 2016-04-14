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
        if (skipSetting == "") {
            functions.saveSetting(this, AppConstant.SKIP_KEY, Integer.toString(AppConstant.DEFAULT_SKIP_AMOUNT));
            skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
        }
        if (splashScreenSetting == "") {
            functions.saveSetting(this, AppConstant.SPLASH_SCREEN_KEY, Boolean.toString(AppConstant.DEFAULT_SPLASH_SCREEN_SETTING));
            splashScreenSetting = functions.readSetting(this, AppConstant.SPLASH_SCREEN_KEY);
        }
        if (lastIntervention == "") {
            //Set file at index 0 as last intervention if there was none saved
            functions.saveSetting(this, AppConstant.CURRENT_INTERVENTION, "0");
            lastIntervention = functions.readSetting(this, AppConstant.CURRENT_INTERVENTION);
        }

        String tempAlbum = functions.getAudioAlbum(this, allAudioFiles.get(Integer.parseInt(lastIntervention)), AppConstant.INTERVENTION_FOLDER);
        String tempTitle = functions.getAudioTitle(this, allAudioFiles.get(Integer.parseInt(lastIntervention)), AppConstant.INTERVENTION_FOLDER);

        selectIntervention(tempTitle, tempAlbum, false);

        // Depending on the settings, either load SplashScreen or MeditationPlayer screen
        if (Boolean.parseBoolean(splashScreenSetting)) {
            Intent loadSplash = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(loadSplash);
        } else {
            Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
            startActivity(loadPlayer);
        }
    }


}
