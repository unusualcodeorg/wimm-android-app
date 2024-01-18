package com.whereismymotivation.ui.content

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.whereismymotivation.R
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChromeTabHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        const val TAG = "ChromeTabHelper"
        private const val STABLE_PACKAGE = "com.android.chrome"
        private const val BETA_PACKAGE = "com.chrome.beta"
        private const val DEV_PACKAGE = "com.chrome.dev"
        private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"
    }

    private var sPackageNameToUse: String? = null

    fun openCustomTab(uri: Uri, fallback: ChromeTabFallback) {
        try {
            val builder = CustomTabsIntent.Builder()

            val defaultColorSchemeParams = CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .build()

            builder.setDefaultColorSchemeParams(defaultColorSchemeParams)

            val customTabsIntent = builder.build()

            val packageName = getPackageNameToUse(context)

            // If we cant find a package name, it means theres no browser that supports
            // Chrome Custom Tabs installed. So, we fallback to the webview
            if (packageName == null) {
                fallback.openUri(context, uri)
            } else {
                customTabsIntent.intent.setPackage(packageName)
                customTabsIntent.launchUrl(context, uri)
            }
        } catch (e: ActivityNotFoundException) {
            Logger.record(e)
        }
    }

    private fun getPackageNameToUse(context: Context): String? {

        if (sPackageNameToUse != null) return sPackageNameToUse

        context.packageManager.let {
            // Get default VIEW intent handler.
            val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"))

            val defaultViewHandlerInfo: ResolveInfo? =
                it.resolveActivity(activityIntent, 0)

            var defaultViewHandlerPackageName: String? = null

            defaultViewHandlerInfo?.let {
                defaultViewHandlerPackageName = defaultViewHandlerInfo.activityInfo.packageName
            }

            val packagesSupportingCustomTabs: ArrayList<String> = ArrayList()

            if (packagesSupportingCustomTabs.isEmpty()) {
                sPackageNameToUse = null
            } else if (packagesSupportingCustomTabs.size == 1) {
                sPackageNameToUse = packagesSupportingCustomTabs[0]
            } else if (!defaultViewHandlerPackageName.isNullOrEmpty()
                && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName)
            ) {
                sPackageNameToUse = defaultViewHandlerPackageName
            } else if (packagesSupportingCustomTabs.contains(STABLE_PACKAGE)) {
                sPackageNameToUse = STABLE_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(BETA_PACKAGE)) {
                sPackageNameToUse = BETA_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(DEV_PACKAGE)) {
                sPackageNameToUse = DEV_PACKAGE
            } else if (packagesSupportingCustomTabs.contains(LOCAL_PACKAGE)) {
                sPackageNameToUse = LOCAL_PACKAGE
            }
        }
        return sPackageNameToUse
    }

    interface ChromeTabFallback {
        fun openUri(context: Context, uri: Uri)
    }
}
