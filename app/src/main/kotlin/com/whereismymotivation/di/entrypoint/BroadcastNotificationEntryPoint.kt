package com.whereismymotivation.di.entrypoint

import com.whereismymotivation.di.component.BroadcastComponent
import com.whereismymotivation.fcm.NotificationBuilder
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@InstallIn(BroadcastComponent::class)
interface BroadcastNotificationEntryPoint {
    fun notificationBuilder(): NotificationBuilder
}