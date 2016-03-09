package com.example.hsport.themindset;

import android.app.Application;
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
