package com.whereismymotivation.work

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DailyMoodAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.run {
                when (action) {
                    AppAlarmManager.ACTION_DAILY_MOOD_RECORD -> {
//                        NotificationBuilder.showMoodNotification(it)
                    }

                    else -> {

                    }
                }
            }
        }
    }
}