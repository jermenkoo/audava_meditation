package com.lambroszannettos.themindmanifesto;

import android.app.Application;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import java.util.concurrent.TimeUnit;

/**
 * Created by Lambros on 08/03/16.
 *
 * Functions in this class will be accessible from all other
 * activities, by typing MyFunctions.theFunctionName([...])
 *
 */
public final class MyFunctions extends Application {

    private MyFunctions() {
    }

    //Function to return time formatted in "mins, seconds" format,
    //given milliseconds
    public static String returnTimeString(double milliseconds) {
        return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) milliseconds),
                TimeUnit.MILLISECONDS.toSeconds((long) milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) milliseconds)));
    }


}
