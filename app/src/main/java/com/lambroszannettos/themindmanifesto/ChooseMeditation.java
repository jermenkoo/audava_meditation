package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.widget.FrameLayout;

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
