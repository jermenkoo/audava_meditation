package com.lambroszannettos.themindmanifesto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Lambros on 28/03/16.
 */
public class HeadphoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    //Do something when headphones are plugged in...
                    Toast.makeText(context, "Unplugged", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //Do something when headphones are plugged out...
                    Toast.makeText(context, "Plugged", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "Headset status error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
