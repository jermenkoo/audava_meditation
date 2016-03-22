package com.lambroszannettos.themindmanifesto;

import android.media.MediaPlayer;

/**
 * Created by Lambros on 22/03/16.
 */
public class MediaPlayerSingleton {

    private static MediaPlayerSingleton mediaPlayerSingleton = new MediaPlayerSingleton();
    private static MediaPlayer mediaPlayer;

    private MediaPlayerSingleton() {
    }

    public static MediaPlayerSingleton getInstance() {
        return mediaPlayerSingleton;
    }

    public static MediaPlayer getMediaPlayerInstance() {
        return mediaPlayer;
    }

}
