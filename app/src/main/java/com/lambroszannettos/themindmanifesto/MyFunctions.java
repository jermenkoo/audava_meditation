package com.lambroszannettos.themindmanifesto;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lambros on 08/03/16.
 *
 * Functions in this class will be accessible from all other
 * activities, by typing MyFunctions.theFunctionName([...])
 */
public final class MyFunctions {

    private static volatile MyFunctions uniqueInstance = null;

    private MyFunctions() {
    }

    /**
     *
     * Function to return time formatted in "mins, seconds" format,
     * given milliseconds.
     *
     * @param milliseconds
     * @return
     */
    public static String returnTimeString(double milliseconds) {

        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) milliseconds) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                        toMinutes((long) milliseconds));

        if ((seconds < 10) && (minutes >= 10)) {
            return String.format("%d:0%d", minutes, seconds);
        } else if ((minutes < 10) && (seconds >= 10)) {
            return String.format("0%d:%d", minutes, seconds);
        } else {
            return String.format("0%d:0%d", minutes, seconds);
        }

    }


    /**
     *
     * Saves a setting in SharedPreferences under the key passed
     * to it.
     *
     * @param context
     * @param key
     * @param value
     */
    public void saveSetting(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(key, value);

        editor.commit();
    }

    /**
     *
     * Returns the string value of a setting that was saved in SharedPreferences,
     * based on the key passed to it.
     *
     * @param context
     * @param key
     * @return
     */
    public String readSetting(Context context, String key) {
        SharedPreferences sharedPref;
        String result = "";

        try {
            sharedPref = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
            result = sharedPref.getString(key, "");
        } catch (NullPointerException e) {
            Log.e("Cannot read settings", e.toString());
        }

        return result;
    }

    /**
     *
     * Given an audio file, this returns the ID3 Title Field.
     *
     * @param context
     * @param file
     * @param path
     * @return
     */
    public String getAudioTitle(Context context, File file, String path) {
        AssetFileDescriptor afd;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            afd = context.getAssets().openFd(path + file.getAbsolutePath());
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        return title;
    }

    /**
     *
     * Given an audio file, returns the ID3 Album Field.
     *
     * @param context
     * @param file
     * @param path
     * @return
     */
    public String getAudioAlbum(Context context, File file, String path) {
        AssetFileDescriptor afd;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            afd = context.getAssets().openFd(path + file.getAbsolutePath());
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String album =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        return album;
    }

    /**
     *
     * Returns unique instance of the MyFunctions class.
     *
     * @return
     */
    public static MyFunctions getUniqueInstance() {

        if (uniqueInstance == null) {
            synchronized (MediaPlayerSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MyFunctions();
                }
            }
        }
        return uniqueInstance;
    }

}
