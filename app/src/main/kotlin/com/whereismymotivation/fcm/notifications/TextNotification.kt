package com.whereismymotivation.fcm.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.fcm.core.Provider
import com.whereismymotivation.utils.log.Logger

class TextNotification(
    private val provider: Provider,
    private val payload: Payload
) : Notification {
    override suspend fun send() {
        try {
            val style = NotificationCompat.BigTextStyle()
                .bigText(payload.message)
                .setBigContentTitle(payload.title)

            val notificationBuilder = provider
                .basicNotificationBuilder()
                .setContentTitle(payload.title)
                .setContentText(payload.subtitle)
                .setAutoCancel(true)
                .setStyle(style)

            val notificationManager =
                provider.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(Notification.Type.TEXT.value, notificationBuilder.build())

        } catch (e: Exception) {
            Logger.record(e)
        }
    }
}