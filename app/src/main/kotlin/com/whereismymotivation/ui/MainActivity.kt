package com.whereismymotivation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.whereismymotivation.R
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    companion object {

        const val TAG = "MainActivity"

        private const val ACTION_SCREEN_NAVIGATION = "ACTION_SCREEN_NAVIGATION"
        private const val KEY_SCREEN_TO_GO = "KEY_SCREEN_TO_GO"

        const val SCREEN_MOOD = "SCREEN_MOOD"
        const val SCREEN_JOURNAL = "SCREEN_JOURNAL"

        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        fun getStartIntent(context: Context, screenToGo: String): Intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                action = ACTION_SCREEN_NAVIGATION
                putExtra(KEY_SCREEN_TO_GO, screenToGo)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                ContextCompat.getColor(this, R.color.immersive_sys_ui)
            )
        )
        super.onCreate(savedInstanceState)
        setContent {
            WimmApp(navigator) { finish() }
        }
    }
}
