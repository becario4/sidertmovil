package com.sidert.sidertmovil.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sidert.sidertmovil.R;

public class ComunicadoCovid extends AppCompatActivity {

    private Context ctx;
    private WebView wvCovid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicado_covid);
        ctx = this;

        wvCovid = findViewById(R.id.wvCovid);
        WebSettings settings = wvCovid.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wvCovid.clearCache(true);
        wvCovid.loadUrl("https://sidert.com.mx/covid19.html");
    }
}
