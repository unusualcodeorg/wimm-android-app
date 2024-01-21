package com.whereismymotivation.work.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import com.whereismymotivation.utils.coroutine.Dispatcher
import com.whereismymotivation.work.AppAlarmManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class DailyMoodAlarmWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dispatcher: Dispatcher,
    private val appAlarmManager: AppAlarmManager,
    private val appMetricPreferences: AppMetricPreferences
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(dispatcher.io()) {
        val time = appMetricPreferences.getDailyRecordAlarmTime()
        appAlarmManager.setDailyMoodAlarm(time.first, time.second, time.third)
        return@withContext Result.success()
    }
}