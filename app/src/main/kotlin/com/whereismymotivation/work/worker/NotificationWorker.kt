package com.whereismymotivation.work.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.whereismymotivation.fcm.NotificationHelper
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.toPayload
import com.whereismymotivation.fcm.core.toStringMap
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val dispatcher: Dispatcher,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(dispatcher.io()) {
        val payload = workerParams.inputData.keyValueMap.toStringMap().toPayload()

        if (payload.type != Notification.Type.UNKNOWN) {
            notificationHelper.showNotification(payload)
        }

        return@withContext Result.success()
    }
}