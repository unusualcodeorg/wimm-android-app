package com.whereismymotivation.init

import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkInit @Inject constructor(
    private val appWorkManager: AppWorkManager,
    private val alarmManager: AppAlarmManager
) : Initializer {
    override fun init() {
        scheduleWorks()
    }

    private fun scheduleWorks() {
        appWorkManager.scheduleDailyMoodNotifyWork()
        alarmManager.setDailyMoodAlarm()
        appWorkManager.addMoodAndJournalSyncWork()
    }
}