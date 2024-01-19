package com.whereismymotivation.ui.common.browser

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.whereismymotivation.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChromeTabHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val TAG = "ChromeTabHelper"
    }

    fun openCustomTab(uri: Uri) {
        val builder = CustomTabsIntent.Builder()

        val defaultColorSchemeParams = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .build()

        builder.setDefaultColorSchemeParams(defaultColorSchemeParams)

        val customTabsIntent = builder.build()

        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        customTabsIntent.launchUrl(context, uri)
    }
}
