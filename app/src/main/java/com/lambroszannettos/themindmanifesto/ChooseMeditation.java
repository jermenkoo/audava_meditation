package com.lambroszannettos.themindmanifesto;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by Lambros on 19/03/16.
 */
public class ChooseMeditation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.choose_med_layout, contentFrameLayout);
    }
}
