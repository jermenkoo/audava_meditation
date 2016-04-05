package com.lambroszannettos.themindmanifesto;
/*
 * Created by lambros on 08/03/16.
 *
 * Just a class to store constants
 *
 */

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AppConstant {
    public static final String MEDITATE = "Audava Meditation";

    // miliseconds
    public static final int SAFE_ENDING = 1000;
    public static final int MAX_SKIP_AMOUNT = 60;

    public static final int DEFAULT_SKIP_AMOUNT = 15;
    public static final boolean DEFAULT_SPLASH_SCREEN_SETTING = true;

    public static final String INTERVENTION_FOLDER = "interventions";

    //Keys for Key/Value pairs for SharedPreferences
    public static final String SKIP_KEY = "skipAmount";
    public static final String SPLASH_SCREEN_KEY = "showSplashScreen";
    public static final String PREVIOUS_INTERVENTION = "previousIntervention";

    public static final DrawerLayout drawerLayout = null;
    public static final ActionBarDrawerToggle actionBarDrawerToggle = null;
    public static final Toolbar toolbar = null;

    public static boolean HEADSET_ON;


}
