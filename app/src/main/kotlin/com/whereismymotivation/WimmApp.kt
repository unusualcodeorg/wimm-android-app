package com.whereismymotivation

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.AppMetricRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.common.SystemUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WimmApp : Application() {

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var appMetricRepository: AppMetricRepository

    override fun onCreate() {
        super.onCreate()
        tracker.trackAppOpen()
        appMetricRepository.setCurrentAppVersion(SystemUtils.getAppVersionCode(this))
        recordUser()
    }

    private fun recordUser() {
        userRepository.getCurrentUser()?.run {
            Firebase.crashlytics.setUserId(userId)
            Firebase.analytics.setUserId(userId)
            userEmail?.let { Firebase.analytics.setUserProperty("Email", it) }
            userName?.let { Firebase.analytics.setUserProperty("Name", it) }
        }
    }

}