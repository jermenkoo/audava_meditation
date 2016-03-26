package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Insert code for reading preferences from disk or performing
        //"first-time run" actions

        //...then load the default screen which is the player
        Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
        startActivity(loadPlayer);

        currentLayoutId = R.id.menu_home;
    }
}
