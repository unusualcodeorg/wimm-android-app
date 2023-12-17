package com.whereismymotivation.init

import android.content.Context
import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager

fun scheduleWorks(context: Context) {
    AppWorkManager.scheduleDailyMoodNotifyWork(context)
    AppAlarmManager.setDailyMoodAlarm(context)
    AppWorkManager.runMoodAndJournalSyncWork(context)
}