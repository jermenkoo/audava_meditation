package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * Created by Lambros on 21/03/16.
 *
 * A simple class that loads a WebView element with the html file
 * containing the text for the about screen. The html file is
 * "about_us.html" and the images associated with it are contained
 * within the same folder, namely the "assets" folder of the app.
 *
 */

public class About extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.about_layout, contentFrameLayout);

        WebView web = (WebView) findViewById(R.id.webView_aboutUs);
        web.loadUrl("file:///android_asset/about_us.html");
    }
}
