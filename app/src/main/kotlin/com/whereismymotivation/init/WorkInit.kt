package com.whereismymotivation.init

import android.app.Application
import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager

fun scheduleWorks(app: Application) {
    AppWorkManager.scheduleDailyMoodNotifyWork(app)
    AppAlarmManager.setDailyMoodAlarm(app)
    AppWorkManager.runMoodAndJournalSyncWork(app)
}