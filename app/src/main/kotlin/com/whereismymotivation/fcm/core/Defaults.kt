package com.whereismymotivation.fcm.core

import android.net.Uri
import androidx.core.app.NotificationCompat.Action

class Defaults(
    val channel: String,
    val ticker: String,
    val color: Int,
    val sound: Uri,
    val smallIcon: Int,
    val largeIcon: Int,
    val openAction: Action
)