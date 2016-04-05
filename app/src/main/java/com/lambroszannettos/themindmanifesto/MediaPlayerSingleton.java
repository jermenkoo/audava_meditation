package com.lambroszannettos.themindmanifesto;

import android.media.MediaPlayer;

import java.io.File;

/**
 * Created by Lambros on 22/03/16.
 */
public class MediaPlayerSingleton {

    private static MediaPlayerSingleton mediaPlayerSingleton = new MediaPlayerSingleton();
    private static MediaPlayer mediaPlayer;
    private static File currentlySelectedIntervention;

    private MediaPlayerSingleton() {
    }

    public static MediaPlayerSingleton getInstance() {
        return mediaPlayerSingleton;
    }

    public static MediaPlayer getMediaPlayerInstance() {
        return mediaPlayer;
    }

    public static File getCurrentlySelectedIntervention() {
        return currentlySelectedIntervention;
    }

    public static void setCurrentlySelectedIntervention(File currentlySelectedIntervention) {
        MediaPlayerSingleton.currentlySelectedIntervention = currentlySelectedIntervention;
    }
}
