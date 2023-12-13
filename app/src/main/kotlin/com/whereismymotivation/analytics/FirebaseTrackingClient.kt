package com.whereismymotivation.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseTrackingClient(private val analytics: FirebaseAnalytics) : TrackingClient {

    override fun track(event: AnalyticsEvent, vararg parameters: AnalyticsEventParam<*>) {

        val newParameters = mutableListOf(*parameters).apply {
            addAll(defaultProperties())
        }

        val bundle = Bundle().apply {
            if (newParameters.isNotEmpty()) {
                for (param in parameters) {
                    when (param.value) {
                        is String -> putString(param.name, param.value)
                        is Int -> putInt(param.name, param.value)
                        is Long -> putLong(param.name, param.value)
                        is Double -> putDouble(param.name, param.value)
                    }
                }
            }
        }
        analytics.logEvent(event.name, bundle)
    }

    override fun defaultProperties(): List<AnalyticsEventParam<*>> {
        return ArrayList()
    }
}