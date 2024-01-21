package com.whereismymotivation.fcm.notifications

import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.app.NotificationCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.fcm.core.Provider
import com.whereismymotivation.utils.common.isValidUrl
import com.whereismymotivation.utils.log.Logger

class ImageNotification(
    private val provider: Provider,
    private val payload: Payload,
    private val imageLoader: ImageLoader
) : Notification {
    override suspend fun send() {
        try {
            if (payload.thumbnail == null || !payload.thumbnail.isValidUrl()) return

            val request = ImageRequest.Builder(provider.context)
                .data(payload.thumbnail)
                .allowHardware(true)
                .build()

            val result = imageLoader.execute(request)
            if (result !is SuccessResult) return

            val bitmap = (result.drawable as BitmapDrawable).bitmap

            val style = NotificationCompat.BigPictureStyle().bigPicture(bitmap)

            val notificationBuilder = provider
                .basicNotificationBuilder()
                .setTicker(payload.ticker)
                .setContentTitle(payload.title)
                .setContentText(payload.subtitle)
                .setStyle(style)
                .setContentIntent(provider.pendingIntents.appOpen())
                .addAction(provider.defaults.openAction)

            val notificationManager =
                provider.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(
                Notification.Type.IMAGE.unique(),
                notificationBuilder.build()
            )

        } catch (e: Exception) {
            Logger.record(e)
        }
    }
}