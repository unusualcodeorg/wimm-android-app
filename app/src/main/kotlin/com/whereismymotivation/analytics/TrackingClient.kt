package com.whereismymotivation.analytics

interface TrackingClient {

    fun track(event: AnalyticsEvent, vararg parameters: AnalyticsEventParam<*>)

    fun defaultProperties(): List<AnalyticsEventParam<*>>
}