package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * Created by Lambros on 21/03/16.
 */
public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.about_layout, contentFrameLayout);

    }
}
