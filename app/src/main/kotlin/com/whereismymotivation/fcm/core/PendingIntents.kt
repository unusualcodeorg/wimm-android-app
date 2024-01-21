package com.whereismymotivation.fcm.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.whereismymotivation.ui.MainActivity
import com.whereismymotivation.ui.navigation.Deeplink

class PendingIntents(private val context: Context) {

    fun appOpen(): PendingIntent =
        buildDeeplinkPendingIntent(Notification.Action.APP_OPEN, Deeplink.MY_BOX)

    fun contentView(contentId: String): PendingIntent =
        buildDeeplinkPendingIntent(Notification.Action.CONTENT_VIEW, Deeplink.APP)

    fun journalRecord(): PendingIntent =
        buildDeeplinkPendingIntent(Notification.Action.JOURNAL_RECORD, Deeplink.APP)

    fun moodRecord(): PendingIntent =
        buildDeeplinkPendingIntent(Notification.Action.MOOD_RECORD, Deeplink.MY_BOX)


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
        deeplink: Deeplink
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            action.requestCode,
            Intent(Intent.ACTION_VIEW, Uri.parse(deeplink.url)),
            PendingIntent.FLAG_IMMUTABLE
        )

}