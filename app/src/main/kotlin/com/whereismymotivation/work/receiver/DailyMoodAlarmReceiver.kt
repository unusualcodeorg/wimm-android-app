package com.whereismymotivation.work.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.whereismymotivation.R
import com.whereismymotivation.fcm.core.Notification
import com.whereismymotivation.fcm.core.Payload
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.work.AppAlarmManager
import com.whereismymotivation.work.AppWorkManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DailyMoodAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appWorkManager: AppWorkManager

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            context?.let {

                intent?.run {
                    when (action) {
                        AppAlarmManager.ACTION_DAILY_MOOD_RECORD -> {
                            val payload = Payload(
                                type = Notification.Type.MOOD,
                                ticker = context.getString(R.string.how_your_feeling),
                                title = context.getString(R.string.notification_mood_title),
                                subtitle = context.getString(R.string.notification_mood_text)
                            )
                            appWorkManager.addNotificationWork(payload)
                        }

                        else -> {

                        }
                    }
                }
            }
        } catch (e: Exception) {
            Logger.record(e)
        }
    }
}