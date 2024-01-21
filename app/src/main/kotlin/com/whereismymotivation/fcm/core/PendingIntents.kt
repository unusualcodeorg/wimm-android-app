package com.whereismymotivation.fcm.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.whereismymotivation.ui.MainActivity
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.profile.ProfileTab

class PendingIntents(private val context: Context) {

    fun appOpen(): PendingIntent =
        buildDeeplinkPendingIntent(
            Notification.Action.APP_OPEN,
            Destination.Home.Feed.deeplink
        )

    fun contentView(contentId: String): PendingIntent =
        buildDeeplinkPendingIntent(
            Notification.Action.CONTENT_VIEW,
            Destination.Content.dynamicDeeplink(contentId)
        )

    fun journalRecord(): PendingIntent =
        buildDeeplinkPendingIntent(
            Notification.Action.JOURNAL_RECORD,
            Destination.Home.Profile.dynamicDeeplink(ProfileTab.JOURNAL.name)
        )

    fun moodRecord(): PendingIntent =
        buildDeeplinkPendingIntent(
            Notification.Action.MOOD_RECORD,
            Destination.Home.Profile.dynamicDeeplink(ProfileTab.MOOD.name)
        )

    private fun buildBundlePendingIntent(
        action: Notification.Action,
        extra: String
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            action.requestCode,
            Intent(context, MainActivity::class.java).apply {
                putExtra(action.name, extra)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            PendingIntent.FLAG_IMMUTABLE
        )

    private fun buildDeeplinkPendingIntent(
        action: Notification.Action,
        deeplink: String
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            action.requestCode,
            Intent(Intent.ACTION_VIEW, Uri.parse(deeplink)),
            PendingIntent.FLAG_IMMUTABLE
        )

}