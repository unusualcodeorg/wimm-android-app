package com.whereismymotivation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.AppMetricRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.init.createDefaultNotificationChannel
import com.whereismymotivation.init.getFcmToken
import com.whereismymotivation.init.recordUser
import com.whereismymotivation.init.scheduleWorks
import com.whereismymotivation.utils.common.SystemUtils
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
        recordUser(userRepository)
        scheduleWorks(this)
        getFcmToken()
        createDefaultNotificationChannel(this)
    }
}