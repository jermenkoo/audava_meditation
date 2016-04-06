package com.lambroszannettos.themindmanifesto;

/*
 * Created by lambros on 08/03/16.
 *
 * Just a class to store constants
 *
 */

public class AppConstant {

    public static final int SAFE_ENDING         = 1000;
    public static final int MAX_SKIP_AMOUNT     = 60;
    public static final int DEFAULT_SKIP_AMOUNT = 15000;

    public static final boolean DEFAULT_SPLASH_SCREEN_SETTING = true;

    public static final String INTERVENTION_FOLDER = "interventions";

    //Keywords for filtering interventions based on selection
    public static final String BROWSE_ALL       = "all";
    public static final String RELATIONSHIPS    = "Relationships";
    public static final String HEALTH           = "Health";
    public static final String BUSINESS         = "Business";

    //Keys for Key/Value pairs for SharedPreferences
    public static final String SKIP_KEY             = "skipAmount";
    public static final String SPLASH_SCREEN_KEY    = "showSplashScreen";
    public static final String CURRENT_INTERVENTION = "previousIntervention";

    public static boolean HEADSET_ON;


}
