package com.whereismymotivation

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.init.CoilInit
import com.whereismymotivation.init.FirebaseInit
import com.whereismymotivation.init.MetricInit
import com.whereismymotivation.init.WorkInit
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WimmApplication : Application(), Configuration.Provider {

    companion object {
        const val TAG = "WimmApplication"
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var firebaseInit: FirebaseInit

    @Inject
    lateinit var workInit: WorkInit

    @Inject
    lateinit var metricInit: MetricInit

    @Inject
    lateinit var coilInit: CoilInit


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        tracker.trackAppOpen()
        metricInit.init()
        workInit.init()
        firebaseInit.init()
        coilInit.init()
    }
}