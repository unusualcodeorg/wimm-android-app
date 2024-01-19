package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetricRepository @Inject constructor(
    private val appMetricPreferences: AppMetricPreferences
) {

    fun setCurrentAppVersion(appVersion: Long) {
        val lastAppVersion = getCurrentAppVersion()
        if (lastAppVersion != appVersion) {
            // app updated or first launch
            appMetricPreferences.setCurrentAppVersion(appVersion)
        }
    }

    fun getCurrentAppVersion(): Long =
        appMetricPreferences.getCurrentAppVersion()

    fun setDailyMoodRecorderNotificationEnable(enable: Boolean) =
        appMetricPreferences.setDailyMoodRecorderNotificationEnable(enable)

    fun isDailyMoodRecorderNotificationEnabled() =
        appMetricPreferences.getDailyMoodRecorderNotificationEnable()

}
