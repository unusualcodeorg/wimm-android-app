package com.whereismymotivation.utils.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

object PermissionUtils {

    fun needAlarmPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    }

    fun needNotificationPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    fun launchAlarmSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissionIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
            context.startActivity(permissionIntent)
        }
    }

    fun launchNotificationSettings(context: Context) {
        val permissionIntent = Intent()

        // Check if the device is running Android 8.0 (Oreo) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissionIntent.run {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
        } else {
            // For older devices, open the app settings screen
            permissionIntent.run {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", context.packageName, null)
            }
        }

        // Start the intent
        context.startActivity(permissionIntent)
    }
}