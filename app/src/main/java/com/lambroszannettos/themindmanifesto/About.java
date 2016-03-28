package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Lambros on 21/03/16.
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
