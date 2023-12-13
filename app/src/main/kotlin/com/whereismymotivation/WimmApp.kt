package com.whereismymotivation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.AppMetricRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.common.Constants
import com.whereismymotivation.utils.common.SystemUtils
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WimmApp : Application(), Configuration.Provider {

    companion object {
        val TAG = "WimmApp"
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var appMetricRepository: AppMetricRepository

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        tracker.trackAppOpen()
        appMetricRepository.setCurrentAppVersion(SystemUtils.getAppVersionCode(this))
        recordUser()
        scheduleWorks()
        getFcmToken()
        createDefaultNotificationChannel()
    }

    private fun recordUser() {
        userRepository.getCurrentUser()?.run {
            Firebase.crashlytics.setUserId(userId)
            Firebase.analytics.setUserId(userId)
            userEmail?.let { Firebase.analytics.setUserProperty("Email", it) }
            userName?.let { Firebase.analytics.setUserProperty("Name", it) }
        }
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.e(
                    TAG,
                    "Fetching FCM registration token failed",
                    task.exception.toString()
                )
                return@addOnCompleteListener
            }
            val token = task.result
            Logger.d(TAG, token)
        }
    }

    private fun createDefaultNotificationChannel() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        Constants.NOTIFICATION_DEFAULT_CHANNEL_ID,
                        getString(R.string.notification_default_channel_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    ).apply {
                        description = getString(R.string.notification_default_channel_description)
                    })

                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        Constants.NOTIFICATION_HAPPINESS_CHANNEL_ID,
                        getString(R.string.notification_happiness_channel_name),
                        NotificationManager.IMPORTANCE_DEFAULT
                    ).apply {
                        description = getString(R.string.notification_happiness_channel_description)
                    })
            }
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private fun scheduleWorks() {
        AppWorkManager.scheduleDailyMoodNotifyWork(this)
        AppAlarmManager.setDailyMoodAlarm(applicationContext)
        AppWorkManager.runMoodAndJournalSyncWork(this)
    }

}