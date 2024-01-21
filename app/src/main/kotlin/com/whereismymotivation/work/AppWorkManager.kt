package com.whereismymotivation.work

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.work.worker.DailyMoodAlarmWorker
import com.whereismymotivation.work.worker.MoodAndJournalSyncWorker
import com.whereismymotivation.work.worker.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppWorkManager @Inject constructor(
    private val workManager: WorkManager
) {

    fun scheduleDailyMoodNotifyWork() {
        workManager.enqueue(
            PeriodicWorkRequest
                .Builder(DailyMoodAlarmWorker::class.java, 6, TimeUnit.HOURS)
                .build()
        )
    }

    fun addMoodAndJournalSyncWork() {
        workManager.enqueue(
            OneTimeWorkRequest
                .Builder(MoodAndJournalSyncWorker::class.java)
                .build()
        )
    }

    fun addNotificationWork(payload: Payload) {
        workManager.enqueue(
            OneTimeWorkRequest
                .Builder(NotificationWorker::class.java)
                .setInputData(Data.Builder().putAll(payload.toMap()).build())
                .build()
        )
    }
}