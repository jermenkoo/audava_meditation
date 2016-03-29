package com.lambroszannettos.themindmanifesto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Read settings if they exist:
        MyFunctions functions = MyFunctions.getUniqueInstance();
        String skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
        String splashScreenSetting = functions.readSetting(this, AppConstant.SPLASH_SCREEN_KEY);

        //If they are null, write the settings with the default values
        if((skipSetting == "") || (splashScreenSetting == "")) {
            functions.saveSetting(this, AppConstant.SKIP_KEY, Integer.toString(AppConstant.DEFAULT_SKIP_AMOUNT));
            functions.saveSetting(this, AppConstant.SPLASH_SCREEN_KEY, Boolean.toString(AppConstant.DEFAULT_SPLASH_SCREEN_SETTING));
        }

        //...then load the default screen which is the player
        Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
        startActivity(loadPlayer);

        currentLayoutId = R.id.menu_home;
    }


}
