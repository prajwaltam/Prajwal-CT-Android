package com.clevertap.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val webView: WebView = findViewById(R.id.webview)

        // Enable JavaScript (optional)
        webView.settings.javaScriptEnabled = true

        // Force links and redirects to open in the WebView instead of in a browser
        webView.webViewClient = WebViewClient()

        // Load a URL
        webView.loadUrl("https://www.example.com")
    }
}