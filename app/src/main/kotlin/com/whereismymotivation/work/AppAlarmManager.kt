package com.whereismymotivation.work

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.whereismymotivation.WimmApplication
import com.whereismymotivation.utils.log.Logger
import java.util.*

object AppAlarmManager {

    const val DAILY_MOOD_REQUEST_CODE = 999
    const val ACTION_DAILY_MOOD_RECORD = "ACTION_DAILY_MOOD_RECORD"

    fun setDailyMoodAlarm(context: Context) {
        if (context is WimmApplication) {
            if (!context.appMetricRepository.isDailyMoodRecorderNotificationEnabled()) return
        }

        try {
            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager? ?: return

            val calendarNow = Calendar.getInstance()
            val calendar8 = Calendar.getInstance().apply {
                set(
                    get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DAY_OF_MONTH),
                    20, 30, 0
                )
            }

            if (calendarNow.after(calendar8)) calendar8.add(Calendar.DAY_OF_MONTH, 1)

            val intent = Intent(context, DailyMoodAlarmReceiver::class.java).apply {
                action = ACTION_DAILY_MOOD_RECORD
            }

            val pIntent = PendingIntent.getBroadcast(
                context, DAILY_MOOD_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                val canSchedule = alarmManager.canScheduleExactAlarms()
                if (canSchedule) scheduleAlarm(alarmManager, calendar8.timeInMillis, pIntent)
            } else {
                scheduleAlarm(alarmManager, calendar8.timeInMillis, pIntent)
            }
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private fun scheduleAlarm(
        alarmManager: AlarmManager,
        mills: Long,
        intent: PendingIntent
    ) {
        alarmManager.setExact(AlarmManager.RTC, mills, intent)
    }
}