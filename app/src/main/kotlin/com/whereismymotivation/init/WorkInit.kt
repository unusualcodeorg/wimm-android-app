package com.whereismymotivation.init

import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkInit @Inject constructor(
    private val appWorkManager: AppWorkManager,
    private val alarmManager: AppAlarmManager,
    private val appMetricPreferences: AppMetricPreferences
) : Initializer {
    override suspend fun init() {
        scheduleWorks()
    }

    private suspend fun scheduleWorks() {
        val time = appMetricPreferences.getDailyRecordAlarmTime()
        alarmManager.setDailyMoodAlarm(time.first, time.second, time.third)

        appWorkManager.addMoodAndJournalSyncWork()

//        appWorkManager.scheduleDailyMoodNotifyWork(): TODO: Check if alarm works without this logic
    }
}