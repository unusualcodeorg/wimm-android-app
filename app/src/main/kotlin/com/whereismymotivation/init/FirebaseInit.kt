package com.whereismymotivation.init

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.R
import com.whereismymotivation.WimmApplication
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseInit @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository,
) : Initializer {
    override suspend fun init() {
        recordUser()
        syncFcmToken()
        createDefaultNotificationChannel()
    }

    private suspend fun recordUser() {
        userRepository.getCurrentUser()?.run {
            Firebase.crashlytics.setUserId(id)
            Firebase.analytics.setUserId(id)
            email?.let { Firebase.analytics.setUserProperty("Email", it) }
            email?.let { Firebase.analytics.setUserProperty("Name", it) }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun syncFcmToken() {
        if (!userRepository.getFirebaseTokenSent() && userRepository.userExists()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Logger.e(
                        WimmApplication.TAG,
                        "Fetching FCM registration token failed",
                        task.exception.toString()
                    )
                    return@addOnCompleteListener
                }
                task.result?.let {
                    GlobalScope.launch {
                        userRepository.setFirebaseToken(it)
                        userRepository.sendFirebaseToken(it)
                            .catch { e ->
                                Logger.record(e)
                            }.collect {
                                userRepository.setFirebaseTokenSent()
                            }
                    }
                    if (BuildConfig.DEBUG) Logger.d(WimmApplication.TAG, it)
                }
            }
        }
    }

    private fun createDefaultNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager =
                    context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(NotificationChannel(
                    context.getString(R.string.default_notification_channel_id),
                    context.getString(R.string.notification_default_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description =
                        context.getString(R.string.notification_default_channel_description)
                })

                notificationManager.createNotificationChannel(NotificationChannel(
                    context.getString(R.string.happiness_notification_channel_id),
                    context.getString(R.string.notification_happiness_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description =
                        context.getString(R.string.notification_happiness_channel_description)
                })
            }
        } catch (e: Exception) {
            Logger.record(e)
        }
    }
}
