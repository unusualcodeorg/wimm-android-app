package com.whereismymotivation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WimmApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}