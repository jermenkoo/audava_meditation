package com.lambroszannettos.themindmanifesto;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Lambros on 26/03/16.
 */
public class SettingsSingleton extends AppCompatActivity{

    private SettingsSingleton uniqueInstance = new SettingsSingleton();

    private SettingsSingleton() {
    }


    public SettingsSingleton getUniqueInstance() {
        return uniqueInstance;
    }
}
