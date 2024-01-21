package com.whereismymotivation.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetricPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {

        private const val CURRENT_APP_VERSION = "PREF_KEY_CURRENT_APP_VERSION"

        private const val DAILY_RECORD_ALARM_TIME_HOUR = "PREF_KEY_DAILY_RECORD_ALARM_TIME_HOUR"
        private const val DAILY_RECORD_ALARM_TIME_MIN = "PREF_KEY_DAILY_RECORD_ALARM_TIME_MIN"
        private const val DAILY_RECORD_ALARM_TIME_SEC = "PREF_KEY_DAILY_RECORD_ALARM_TIME_SEC"

        private const val DAILY_MOOD_RECORDER_NOTIFICATION =
            "PREF_KEY_DAILY_MOOD_RECORDER_NOTIFICATION"

    }

    fun setCurrentAppVersion(appVersion: Long) =
        prefs.edit().putLong(CURRENT_APP_VERSION, appVersion).apply()

    fun getCurrentAppVersion(): Long =
        prefs.getLong(CURRENT_APP_VERSION, 0)

    fun setDailyRecordAlarmTime(hour: Int, min: Int, sec: Int) {
        prefs.edit().putInt(DAILY_RECORD_ALARM_TIME_HOUR, hour).apply()
        prefs.edit().putInt(DAILY_RECORD_ALARM_TIME_MIN, min).apply()
        prefs.edit().putInt(DAILY_RECORD_ALARM_TIME_SEC, sec).apply()
    }

    fun getDailyRecordAlarmTime(): Triple<Int, Int, Int> {
        val hour = prefs.getInt(DAILY_RECORD_ALARM_TIME_HOUR, 20) // 8PM
        val min = prefs.getInt(DAILY_RECORD_ALARM_TIME_MIN, 0)
        val sec = prefs.getInt(DAILY_RECORD_ALARM_TIME_SEC, 0)
        return Triple(hour, min, sec)
    }

    fun setDailyMoodRecorderNotificationEnable(enable: Boolean) =
        prefs.edit().putBoolean(DAILY_MOOD_RECORDER_NOTIFICATION, enable).apply()

    fun getDailyMoodRecorderNotificationEnable() =
        prefs.getBoolean(DAILY_MOOD_RECORDER_NOTIFICATION, true)

}