package com.lambroszannettos.themindmanifesto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lambros on 29/02/16.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the content view to the Splash Screen layout,
        //which then pauses/sleeps for 3 seconds (within a Thread
        //so that the phone does not otherwise freeze up) and then
        //calls the MainActivity which then loads up the main layout
        setContentView(R.layout.splash_screen);

        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent loadPlayer = new Intent(getApplicationContext(), MeditationPlayer.class);
                    startActivity(loadPlayer);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
