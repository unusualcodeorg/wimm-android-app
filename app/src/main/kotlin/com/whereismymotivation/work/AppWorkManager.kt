package com.whereismymotivation.work

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


object AppWorkManager {

    fun scheduleDailyMoodNotifyWork(context: Context) {
        WorkManager
            .getInstance(context)
            .enqueue(
                PeriodicWorkRequest
                    .Builder(DailyMoodNotifyWorker::class.java, 6, TimeUnit.HOURS)
                    .build()
            )
    }

    fun runMoodAndJournalSyncWork(context: Context) {
        WorkManager
            .getInstance(context)
            .enqueue(
                OneTimeWorkRequest
                    .Builder(MoodAndJournalSyncWorker::class.java)
                    .build()
            )
    }
}