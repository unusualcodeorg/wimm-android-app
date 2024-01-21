package com.whereismymotivation.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.di.qualifier.ScopeIO
import com.whereismymotivation.fcm.core.toPayload
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.work.AppWorkManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    @ScopeIO
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var notificationHelper: NotificationHelper

    @Inject
    lateinit var appWorkManager: AppWorkManager

    companion object {
        private const val TAG = "NotificationService"
    }

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var tracker: Tracker

    /**
     * There are two types of messages data messages and notification messages. Data messages are handled
     * here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
     * traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
     * is in the foreground. When the app is in the background an automatically generated notification is displayed.
     * When the user taps on the notification they are returned to the app. Messages containing both notification
     * and data payloads are treated as notification messages. The Firebase console always sends notification
     * messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Logger.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.let {
            if (it.isNotEmpty()) {
                Logger.d(TAG, "Message data payload: $it")

                /**
                 * Check if data needs to be processed by long running job
                 * For long-running tasks (10 seconds or more) use WorkManager.
                 * scheduleJob()
                 * OR
                 * Handle message within 10 seconds
                 * handleNow()
                 */
                appWorkManager.addNotificationWork(it.toPayload())
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.body?.let {
            Logger.d(TAG, "Message Notification Body: $it")
            scope.launch {
                notificationHelper.showMessage(it)
            }
        }

        tracker.trackServerNotificationReceived()
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        scope.launch {
            if (userRepository.userExists()) {
                userRepository.sendFirebaseToken(token)
                    .catch { }
                    .collect {
                        userRepository.setFirebaseToken(it)
                    }
            }
        }
        Logger.d(TAG, "Refreshed token: $token")
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}