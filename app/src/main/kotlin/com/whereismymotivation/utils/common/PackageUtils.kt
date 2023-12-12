package com.whereismymotivation.utils.common

import android.content.Context
import android.content.pm.PackageManager

object PackageUtils {

    fun getAppVersionCode(context: Context): Int {
        return getAppVersionCode(context, context.packageName)
    }

    @Suppress("DEPRECATION")
    private fun getAppVersionCode(context: Context, packageName: String): Int {
        try {
            return context.packageManager
                .getPackageInfo(packageName, 0)?.run {
                    return@run versionCode
                } ?: Constants.NULL_INDEX
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return Constants.NULL_INDEX
        }
    }
}