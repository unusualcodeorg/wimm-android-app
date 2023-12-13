package com.whereismymotivation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.AppMetricRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.common.SystemUtils
import com.whereismymotivation.utils.display.Toaster
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
            Toaster.show(applicationContext, token)
        }
    }

    private fun scheduleWorks() {
        AppWorkManager.scheduleDailyMoodNotifyWork(this)
        AppAlarmManager.setDailyMoodAlarm(applicationContext)
        AppWorkManager.runMoodAndJournalSyncWork(this)
    }

}