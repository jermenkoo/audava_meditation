package com.lambroszannettos.themindmanifesto;

import android.media.MediaPlayer;

/**
 * Created by Lambros on 22/03/16.
 */
public class MediaPlayerSingleton {

    /*
    *  This singleton class ensures that only one instance of the MediaPlayer Object
    *  exists in the program at any given time. When the getInstance() function of this class
    *  is called, it checks to see whether the static variable "instance" is null
    *  (i.e. there are no previous instances of the class around, true when the
    *  app is first run), and if so, it creates an instance.
    *  It then returns that instance.
    *
    *  The MediaPlayer Object in this class is the only one used throughout the app.
    *
    * */

    private static volatile MediaPlayerSingleton instance = null;
    MediaPlayer mediaPlayer = new MediaPlayer();

    private MediaPlayerSingleton() {
    }

    public static MediaPlayerSingleton getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerSingleton.class) {
                if (instance == null) {
                    instance = new MediaPlayerSingleton();
                }
            }
        }

        return instance;
    }
}
