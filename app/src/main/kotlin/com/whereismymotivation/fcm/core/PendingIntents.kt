package com.whereismymotivation.fcm.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.whereismymotivation.ui.MainActivity

class PendingIntents(private val context: Context) {

    fun appOpen(): PendingIntent =
        buildPendingIntent(Notification.Action.APP_OPEN)

    fun contentView(contentId: String): PendingIntent =
        buildPendingIntent(Notification.Action.CONTENT_VIEW, contentId)

    fun journalRecord(): PendingIntent =
        buildPendingIntent(Notification.Action.JOURNAL_RECORD)

    fun moodRecord(): PendingIntent =
        buildPendingIntent(Notification.Action.MOOD_RECORD)

    private fun buildPendingIntent(
        action: Notification.Action,
        extra: String? = null
    ): PendingIntent =
        PendingIntent.getActivity(
            context,
            action.requestCode,
            Intent(context, MainActivity::class.java).apply {
                putExtra(action.name, extra ?: action.requestCode)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

}