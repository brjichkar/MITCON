package com.android.mitcontaskmanagement.ui.mainScreen.about

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import com.android.mitcontaskmanagement.R
import  android.webkit.WebSettings

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://mitconskills.in/admin/calendar")
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
      //  webView.settings.setLayoutAlgorithm(webView.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.settings.setJavaScriptEnabled(true);
        webView.settings.setLoadWithOverviewMode(true);
        webView.settings.setUseWideViewPort(true);
        webView.settings.setBuiltInZoomControls(true);
        webView.settings.setPluginState(WebSettings.PluginState.ON);
    }

    // if you press Back button this code will work
    override fun onBackPressed() {

        if (webView.canGoBack())
            webView.goBack()

        else
            super.onBackPressed()
    }
}
