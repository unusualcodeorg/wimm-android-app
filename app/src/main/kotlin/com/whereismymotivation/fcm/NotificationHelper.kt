package com.whereismymotivation.fcm

import coil.ImageLoader
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.fcm.core.Provider
import com.whereismymotivation.fcm.notifications.ContentNotification
import com.whereismymotivation.fcm.notifications.ImageNotification
import com.whereismymotivation.fcm.notifications.MoodNotification
import com.whereismymotivation.fcm.notifications.TextNotification
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val imageLoader: ImageLoader,
    private val provider: Provider,
) {

    suspend fun showNotification(payload: Payload) {
        when (payload.type) {
            Notification.Type.TEXT ->
                TextNotification(provider, payload).send()

            Notification.Type.IMAGE ->
                ImageNotification(provider, payload, imageLoader).send()

            Notification.Type.MOOD ->
                MoodNotification(provider, payload).send()

            Notification.Type.CONTENT -> {
                ContentNotification(provider, payload, imageLoader).send()
            }

            Notification.Type.TEXT_AND_IMAGE -> {}

            Notification.Type.UNKNOWN -> {}
        }
    }

    suspend fun showMessage(messageBody: String) {
        val payload = Payload(
            type = Notification.Type.TEXT,
            ticker = provider.defaults.ticker,
            subtitle = "",
            title = messageBody,
        )
        TextNotification(provider, payload).send()
    }

}