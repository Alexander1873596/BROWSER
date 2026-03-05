package com.luma.browser

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)

        // WebView settings
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            mediaPlaybackRequiresUserGesture = false
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true

            // Allow local file -> network requests (some UIs fetch stuff)
            @Suppress("DEPRECATION")
            if (android.os.Build.VERSION.SDK_INT >= 16) {
                allowUniversalAccessFromFileURLs = true
                allowFileAccessFromFileURLs = true
            }

            // Helpful for some sites
            userAgentString = userAgentString + " LUMA-Android"
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }

        webView.webChromeClient = WebChromeClient()

        // Load your existing UI from assets
        webView.loadUrl("file:///android_asset/src/index.html")

        // Back button: go back in WebView history
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack() else finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            webView.destroy()
        } catch (_: Throwable) {
        }
    }
}
