package com.whereismymotivation.init

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.whereismymotivation.R
import com.whereismymotivation.WimmApplication
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.common.Constants
import com.whereismymotivation.utils.log.Logger

fun recordUser(userRepository: UserRepository) {
    userRepository.getCurrentUser()?.run {
        Firebase.crashlytics.setUserId(id)
        Firebase.analytics.setUserId(id)
        email?.let { Firebase.analytics.setUserProperty("Email", it) }
        email?.let { Firebase.analytics.setUserProperty("Name", it) }
    }
}

fun getFcmToken() {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Logger.e(
                WimmApplication.TAG,
                "Fetching FCM registration token failed",
                task.exception.toString()
            )
            return@addOnCompleteListener
        }
        val token = task.result
        Logger.d(WimmApplication.TAG, token)
    }
}

fun createDefaultNotificationChannel(app: Application) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager =
                app.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    Constants.NOTIFICATION_DEFAULT_CHANNEL_ID,
                    app.getString(R.string.notification_default_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = app.getString(R.string.notification_default_channel_description)
                })

            notificationManager.createNotificationChannel(
                NotificationChannel(
                    Constants.NOTIFICATION_HAPPINESS_CHANNEL_ID,
                    app.getString(R.string.notification_happiness_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = app.getString(R.string.notification_happiness_channel_description)
                })
        }
    } catch (e: Exception) {
        Logger.record(e)
    }
}
