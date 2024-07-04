package com.whereismymotivation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.whereismymotivation.R
import com.whereismymotivation.utils.common.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                ContextCompat.getColor(this, R.color.immersive_sys_ui)
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            WimmApp(
                navigator = viewModel.navigator,
                loader = viewModel.loader,
                messenger = viewModel.messenger,
                sharer = viewModel.sharer,
                finish = { finish() }
            )
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.run {
            when (action) {
                Intent.ACTION_SEND -> {
                    if (type == Constants.MIME_TYPE_TEXT_PLAIN) {
                        val text = getStringExtra(Intent.EXTRA_TEXT)
                        viewModel.storeMotivation(text)
                    }
                }

                Intent.ACTION_VIEW -> {
                    viewModel.handleDeepLink(data)
                }
            }
        }
    }
}
