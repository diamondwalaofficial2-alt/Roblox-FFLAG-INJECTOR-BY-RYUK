package com.example.fflagstrapper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private var pendingExport = ""

    private val openJson = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let { readJson(it) }
    }
    private val saveJson = registerForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
        uri?.let { writeJson(it, pendingExport) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(AndroidBridge(), "Android")
        setContentView(webView)
        webView.loadUrl("file:///android_asset/index.html")
    }

    inner class AndroidBridge {
        @JavascriptInterface fun importJson() {
            runOnUiThread { openJson.launch(arrayOf("application/json", "text/*")) }
        }
        @JavascriptInterface fun exportJson(content: String) {
            pendingExport = content
            runOnUiThread { saveJson.launch("fflags-config.json") }
        }
        @JavascriptInterface fun toast(message: String) {
            runOnUiThread { Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show() }
        }
        @JavascriptInterface fun appVersion(): String = "1.0"
    }

    private fun readJson(uri: Uri) {
        try {
            val content = contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() } ?: ""
            JSONObject(content)
            val safe = JSONObject.quote(content)
            webView.evaluateJavascript("window.loadImportedJson($safe)", null)
            Toast.makeText(this, "Configuration imported", Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
            Toast.makeText(this, "Invalid JSON file", Toast.LENGTH_LONG).show()
        }
    }

    private fun writeJson(uri: Uri, content: String) {
        try {
            contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { it.write(content) }
            Toast.makeText(this, "Configuration exported", Toast.LENGTH_SHORT).show()
        } catch (_: Exception) {
            Toast.makeText(this, "Could not save file", Toast.LENGTH_LONG).show()
        }
    }
}
