package com.whereismymotivation.ui.content

import android.annotation.SuppressLint
import android.graphics.Color
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.whereismymotivation.ui.theme.black
import com.whereismymotivation.utils.common.UrlUtils
import com.whereismymotivation.utils.log.Logger
import kotlin.math.roundToInt


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlayer(url: String) {

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screen = remember {
        with(density) {
            val width = configuration.screenWidthDp.dp.toPx()
            val height = (width * 1080) / 1920
            return@remember Pair(width.roundToInt(), height.roundToInt())
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(with(LocalDensity.current) { screen.second.toDp() })
            .background(MaterialTheme.colorScheme.black),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        Logger.d("WebChromeClient", consoleMessage?.message() ?: "")
                        return super.onConsoleMessage(consoleMessage)
                    }
                }
                settings.run {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(true)
                    mediaPlaybackRequiresUserGesture = false;
                }
                setBackgroundColor(Color.BLACK)
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            }
        },
        update = { webView ->
            UrlUtils.parseYoutubeUrlForId(url)?.let {
                webView.loadData(
                    youtubeIframe(screen.first, screen.second, it),
                    "text/html",
                    "UTF-8"
                )
            }
        },
    )
}

@Composable
fun YouTubePlaceholder() {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screen = remember {
        with(density) {
            val width = configuration.screenWidthDp.dp.toPx()
            val height = (width * 1080) / 1920
            return@remember Pair(width.roundToInt(), height.roundToInt())
        }
    }
    Box(
        modifier = Modifier
            .width(with(LocalDensity.current) { screen.first.toDp() })
            .height(with(LocalDensity.current) { screen.second.toDp() })
            .background(MaterialTheme.colorScheme.black)
    )
}

@Preview(showBackground = true)
@Composable
private fun YouTubePlaceholderPreview() {
    YouTubePlaceholder()
}

private fun youtubeIframe(width: Int, height: Int, videoId: String) = """
<!DOCTYPE html>
<html>
  <body>
    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
    <div id="player"></div>

    <script>
      // 2. This code loads the IFrame Player API code asynchronously.
      var tag = document.createElement('script');

      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName('script')[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

      // 3. This function creates an <iframe> (and YouTube player)
      //    after the API code downloads.
      var player;
      function onYouTubeIframeAPIReady() {
        player = new YT.Player('player', {
          height: '$height',
          width: '$width',
          videoId: '$videoId',
          playerVars: {
            'playsinline': 1,
            'autoplay': 1,
            'color': 'red',
            'controls': 1,
            'rel': 0,
            // 'start': 0,
            'enablejsapi': 1
          },
          events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
          }
        });
      }

      // 4. The API will call this function when the video player is ready.
      function onPlayerReady(event) {
        event.target.playVideo();
      }

      // 5. The API calls this function when the player's state changes.
      //    The function indicates that when playing a video (state=1),
      function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING) {
          console.log(event.data)
        }
      }
      function stopVideo() {
        player.stopVideo();
      }
    </script>
  </body>
</html>
""".trimIndent()