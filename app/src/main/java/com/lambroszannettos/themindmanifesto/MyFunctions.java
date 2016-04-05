package com.lambroszannettos.themindmanifesto;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lambros on 08/03/16.
 * <p/>
 * Functions in this class will be accessible from all other
 * activities, by typing MyFunctions.theFunctionName([...])
 */
public final class MyFunctions extends Application {

    private static MyFunctions uniqueInstance = new MyFunctions();

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


    public void saveSetting(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);

        editor.commit();

    }

    public String readSetting(Context context,String key) {

        SharedPreferences sharedPref;
        String result = "";

        try {
            sharedPref = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
            result = sharedPref.getString(key, "");
        } catch (NullPointerException e) {
            Log.d("Cannot read settings", e.toString());
        }

        return result;
    }


    public ArrayList<File> getAllFilesInAssetByExtension(Context context, String subFolder, String extension) {
        Assert.assertNotNull(context);

        try {
            String[] files = context.getAssets().list(subFolder);
            ArrayList<File> filesWithExtension = new ArrayList<>();

            for (String file : files) {
                if (file.endsWith(extension)) {
                    filesWithExtension.add(new File(file));
                }
            }

            return filesWithExtension;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MyFunctions getUniqueInstance() {
        return uniqueInstance;
    }

}
