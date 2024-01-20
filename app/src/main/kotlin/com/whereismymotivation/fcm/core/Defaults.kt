package com.whereismymotivation.fcm.core

import android.graphics.Bitmap
import android.net.Uri
import androidx.core.app.NotificationCompat.Action

class Defaults(
    val channel: String,
    val ticker: String,
    val color: Int,
    val sound: Uri,
    val smallIcon: Int,
    val largeIcon: Bitmap,
    val openAction: Action
)