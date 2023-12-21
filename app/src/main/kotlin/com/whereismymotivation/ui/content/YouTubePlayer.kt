package com.whereismymotivation.ui.content

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.whereismymotivation.utils.common.UrlUtils

@Composable
fun YouTubePlayer(modifier: Modifier = Modifier, viewModel: ContentViewModel) {
    val content = viewModel.content.collectAsState().value ?: return

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        YouTubeWebView(url = content.extra)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun YouTubeWebView(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(true)
                }
                setBackgroundColor(Color.BLACK)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            }
        },
        update = { webView ->
            webView.loadData(youtubeIframe(url), "text/html", "UTF-8")
        },
        modifier = Modifier.fillMaxSize()
    )
}

private fun youtubeIframe(url: String) = """
            <html>
            <style>
            .container {
                position: relative;
                width: 100%;
                height: 0;
                padding-bottom: 56.25%;
            }
            .video {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;

            }
            @media(max-width: 600px) {
                .video {
                    top: 50%;
                    transform: translateY(50%);
                }
            }
            body {
                margin: 0;
            }
            </style>
                <body>
                    <div class="container">
                        <iframe class="video" src='${getYouTubeLink(url)}' frameborder="0" allowfullscreen
                            allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture'>
                        </iframe>
                    </div>
                </body>
            </html>
        """.trimIndent()

private fun getYouTubeLink(url: String) =
    UrlUtils.parseYoutubeUrlForId(url)?.let {
        return@let "http://www.youtube.com/embed/$it?autoplay=1"
    } ?: url