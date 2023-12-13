package com.whereismymotivation.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class DailyMoodNotifyWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dispatcher: Dispatcher
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(dispatcher.io()) {
        AppAlarmManager.setDailyMoodAlarm(context)
        return@withContext Result.success()
    }
}