package com.whereismymotivation.utils.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat

object SystemUtils {

    private const val NO_VERSION_NAME = ""

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)


    fun getAppVersionCode(context: Context): Long {
        return PackageInfoCompat.getLongVersionCode(
            context.packageManager.getPackageInfo(
                context.packageName,
                0
            )
        )
    }

    fun getAppVersionName(context: Context): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(
                context.packageName,
                0
            )
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            NO_VERSION_NAME
        }
    }

}