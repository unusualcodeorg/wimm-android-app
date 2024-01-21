package com.whereismymotivation.fcm

import coil.ImageLoader
import com.whereismymotivation.di.qualifier.ServiceScopeIO
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.fcm.core.Provider
import com.whereismymotivation.fcm.core.toPayload
import com.whereismymotivation.fcm.notifications.ImageNotification
import com.whereismymotivation.fcm.notifications.MoodNotification
import com.whereismymotivation.fcm.notifications.TextNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationBuilder @Inject constructor(
    @ServiceScopeIO private val scope: CoroutineScope,
    private val imageLoader: ImageLoader,
    private val provider: Provider,
) {

    fun showNotification(data: Map<String, String>) {
        showNotification(data.toPayload())
    }

    fun showNotification(payload: Payload) {
        scope.launch {
            when (payload.type) {
                Notification.Type.TEXT ->
                    TextNotification(provider, payload).send()

                Notification.Type.IMAGE ->
                    ImageNotification(provider, payload, imageLoader).send()

                Notification.Type.MOOD ->
                    MoodNotification(provider, payload).send()

                Notification.Type.TEXT_AND_IMAGE -> {}
                Notification.Type.CONTENT -> {}

                Notification.Type.UNKNOWN -> {}
            }
        }
    }

    fun showMessage(messageBody: String) {
        scope.launch {
            val payload = Payload(
                type = Notification.Type.TEXT,
                ticker = provider.defaults.ticker,
                subtitle = "",
                title = messageBody,
            )
            TextNotification(provider, payload).send()
        }
    }

}