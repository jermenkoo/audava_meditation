package com.lambroszannettos.themindmanifesto;

/*
 * Created by lambros on 08/03/16.
 *
 * Just a class to store constants
 *
 */

import java.util.HashMap;

public class AppConstant {

    //Constants for songs
    public static final int SAFE_ENDING         = 1000;
    public static final int MAX_SKIP_AMOUNT     = 60;
    public static final int DEFAULT_SKIP_AMOUNT = 15000;

    public static final boolean DEFAULT_SPLASH_SCREEN_SETTING = true;

    public static final String INTERVENTION_FOLDER = "interventions";

    //Keywords for filtering interventions based on selection
    public static final String BROWSE_ALL       = "All";
    public static final String RELATIONSHIPS    = "Relationships";
    public static final String HEALTH           = "Health";
    public static final String BUSINESS         = "Business";

    //Keys for Key/Value pairs for SharedPreferences
    public static final String SKIP_KEY             = "skipAmount";
    public static final String SPLASH_SCREEN_KEY    = "showSplashScreen";
    public static final String CURRENT_INTERVENTION = "previousIntervention";

    // choosing menu items
    public static final HashMap<Integer, Class> choiceToScreen = new HashMap<Integer, Class>() {{
        put(R.id.menu_home,          MeditationPlayer.class);
        put(R.id.menu_browse_all,    ChooseMeditation.class);
        put(R.id.menu_relationships, ChooseMeditation.class);
        put(R.id.menu_health,        ChooseMeditation.class);
        put(R.id.menu_business,      ChooseMeditation.class);
        put(R.id.menu_about,         About.class);
        put(R.id.menu_settings,      SettingsActivity.class);
    }};

    // InterventionCategory
    public static final HashMap<Integer, String> categoryToString = new HashMap<Integer, String>() {{
        put(R.id.menu_browse_all,    BROWSE_ALL);
        put(R.id.menu_relationships, RELATIONSHIPS);
        put(R.id.menu_health,        HEALTH);
        put(R.id.menu_business,      BUSINESS);
    }};

    public static boolean HEADSET_ON;


}
