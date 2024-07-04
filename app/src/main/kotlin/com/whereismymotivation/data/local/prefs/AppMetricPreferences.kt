package com.whereismymotivation.data.local.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetricPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val CURRENT_APP_VERSION = longPreferencesKey("CURRENT_APP_VERSION")
        private val DAILY_RECORD_ALARM_TIME_HOUR = intPreferencesKey("DAILY_RECORD_ALARM_TIME_HOUR")
        private val DAILY_RECORD_ALARM_TIME_MIN = intPreferencesKey("DAILY_RECORD_ALARM_TIME_MIN")
        private val DAILY_RECORD_ALARM_TIME_SEC = intPreferencesKey("DAILY_RECORD_ALARM_TIME_SEC")
        private val DAILY_MOOD_RECORDER_NOTIFICATION =
            booleanPreferencesKey("DAILY_MOOD_RECORDER_NOTIFICATION")
    }

    suspend fun setCurrentAppVersion(appVersion: Long) {
        dataStore.edit { it[CURRENT_APP_VERSION] = appVersion }
    }

    suspend fun getCurrentAppVersion() =
        dataStore.data.map { it[CURRENT_APP_VERSION] ?: 0 }.first()

    suspend fun setDailyRecordAlarmTime(hour: Int, min: Int, sec: Int) {
        dataStore.edit { it[DAILY_RECORD_ALARM_TIME_HOUR] = hour }
        dataStore.edit { it[DAILY_RECORD_ALARM_TIME_MIN] = min }
        dataStore.edit { it[DAILY_RECORD_ALARM_TIME_SEC] = sec }
    }

    suspend fun getDailyRecordAlarmTime(): Triple<Int, Int, Int> {
        val hour = dataStore.data.map { it[DAILY_RECORD_ALARM_TIME_HOUR] ?: 22 }.first() // 8PM
        val min = dataStore.data.map { it[DAILY_RECORD_ALARM_TIME_MIN] ?: 34 }.first()
        val sec = dataStore.data.map { it[DAILY_RECORD_ALARM_TIME_SEC] ?: 0 }.first()
        return Triple(hour, min, sec)
    }

    suspend fun setDailyMoodRecorderNotificationEnable(enable: Boolean) {
        dataStore.edit { it[DAILY_MOOD_RECORDER_NOTIFICATION] = enable }
    }

    suspend fun getDailyMoodRecorderNotificationEnable() =
        dataStore.data.map { it[DAILY_MOOD_RECORDER_NOTIFICATION] ?: true }.first()
}