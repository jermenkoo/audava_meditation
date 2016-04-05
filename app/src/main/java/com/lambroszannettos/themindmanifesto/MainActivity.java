package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Read settings if they exist:
        MyFunctions functions = MyFunctions.getUniqueInstance();
        String skipSetting = functions.readSetting(this, AppConstant.SKIP_KEY);
        String splashScreenSetting = functions.readSetting(this, AppConstant.SPLASH_SCREEN_KEY);
        String previousIntervention = functions.readSetting(this, AppConstant.PREVIOUS_INTERVENTION);

        //If they are null, write the settings with the default values
        if((skipSetting == "") || (splashScreenSetting == "")) {
            functions.saveSetting(this, AppConstant.SKIP_KEY, Integer.toString(AppConstant.DEFAULT_SKIP_AMOUNT));
            functions.saveSetting(this, AppConstant.SPLASH_SCREEN_KEY, Boolean.toString(AppConstant.DEFAULT_SPLASH_SCREEN_SETTING));
        }

        if(previousIntervention == "") {
            ArrayList<File> files = functions.getAllFilesInAssetByExtension(this, AppConstant.INTERVENTION_FOLDER, ".m4a");
            functions.saveSetting(this, AppConstant.PREVIOUS_INTERVENTION, files.get(0).toString());
        }

        //...then load the default screen which is the player
        Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
        startActivity(loadPlayer);

        currentLayoutId = R.id.menu_home;
    }


}
