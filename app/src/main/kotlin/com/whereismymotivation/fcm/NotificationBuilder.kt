package com.whereismymotivation.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.whereismymotivation.R
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.di.ScopeMain
import com.whereismymotivation.utils.common.Constants
import com.whereismymotivation.utils.common.ContentUtils
import com.whereismymotivation.utils.coroutine.Dispatcher
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.utils.share.BitmapUtils
import com.whereismymotivation.utils.share.QuoteUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ServiceScoped
class NotificationBuilder @Inject constructor(
    @ScopeMain private val scope: CoroutineScope,
    @ApplicationContext private val context: Context,
    private val tracker: Tracker
) {

    companion object {
        const val NOTIFICATION_TYPE = "type"

        const val NOTIFICATION_TYPE_UNKNOWN = "UNKNOWN"
        const val NOTIFICATION_TYPE_TEXT = "TEXT"
        const val NOTIFICATION_TYPE_IMAGE = "IMAGE"
        const val NOTIFICATION_TYPE_TEXT_AND_IMAGE = "TEXT_AND_IMAGE"
        const val NOTIFICATION_TYPE_CONTENT = "CONTENT"
        const val NOTIFICATION_TYPE_MOOD = "MOOD"

        const val NOTIFICATION_TYPE_UNKNOWN_ID = 1000
        const val NOTIFICATION_TYPE_TEXT_ID = 2000
        const val NOTIFICATION_TYPE_TEXT_AND_IMAGE_ID = 3000
        const val NOTIFICATION_TYPE_IMAGE_ID = 4000
        const val NOTIFICATION_TYPE_CONTENT_ID = 5000
        const val NOTIFICATION_TYPE_MOOD_ID = 6000

        const val NOTIFICATION_ACTION = "NOTIFICATION_ACTION"

        const val ACTION_APP_LAUNCH = "ACTION_APP_LAUNCH"
        const val ACTION_CLOSE = "ACTION_CLOSE"
        const val ACTION_CONTENT_VIEW = "ACTION_CONTENT_VIEW"
        const val ACTION_CONTENT_SHARE_OTHERS = "ACTION_CONTENT_SHARE_OTHERS"
        const val ACTION_CONTENT_SHARE_WHATSAPP = "ACTION_CONTENT_SHARE_WHATSAPP"
        const val ACTION_MOOD_RECORD = "ACTION_MOOD_RECORD"
        const val ACTION_JOURNAL_RECORD = "ACTION_JOURNAL_RECORD"

        const val NOTIFICATION_DATA_CONTENT = "NOTIFICATION_DATA_CONTENT"

        const val ACTION_ID_1 = 1
        const val ACTION_ID_2 = 2
        const val ACTION_ID_3 = 3
        const val ACTION_ID_4 = 4
        const val ACTION_ID_5 = 5
        const val ACTION_ID_6 = 6
        const val ACTION_ID_7 = 7
    }

    fun showFcmNotification(data: Map<String, String>) {
        getString(data, "type", NOTIFICATION_TYPE_UNKNOWN)?.let {
            when (it) {
                NOTIFICATION_TYPE_TEXT ->
                    showTextNotification(data)

                NOTIFICATION_TYPE_TEXT_AND_IMAGE ->
                    downloadAndShowImageNotification(
                        data,
                        showTextAndImageNotification
                    )

                NOTIFICATION_TYPE_IMAGE ->
                    downloadAndShowImageNotification(data, ::showImageNotification)

                NOTIFICATION_TYPE_CONTENT -> {
                    ContentUtils.convertToContent(data)?.run {
                        when (category) {
                            Content.Category.QUOTE ->
                                downloadAndShowImageNotification(
                                    data,
                                    ::showQuoteContentNotification
                                )

                            else ->
                                downloadAndShowImageNotification(
                                    data,
                                    ::showContentNotification
                                )
                        }
                    } ?: downloadAndShowImageNotification(
                        data,
                        ::showContentNotification
                    )
                }
            }
        }
    }

    fun showFcmNotification(messageBody: String) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder =
            NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_wimm_notification)
                .setLargeIcon(
                    (ContextCompat.getDrawable(
                        context,
                        R.drawable.wimm_logo
                    ) as BitmapDrawable).bitmap
                )
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(getAppOpenPendingIntent(NOTIFICATION_TYPE_UNKNOWN))

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(NOTIFICATION_TYPE_UNKNOWN_ID, notificationBuilder.build())

        tracker.trackServerNotificationShown()
    }

    private fun showTextNotification(
        data: Map<String, String>,
    ) {
        try {
            val openIntent = getAppOpenPendingIntent(NOTIFICATION_TYPE_TEXT)
            val closeIntent = getAppClosePendingIntent(NOTIFICATION_TYPE_TEXT)

            val notificationBuilder =
                NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_wimm_notification)
                    .setLargeIcon(
                        (ContextCompat
                            .getDrawable(context, R.drawable.wimm_logo) as BitmapDrawable)
                            .bitmap
                    )
                    .setTicker(
                        getString(
                            data,
                            "ticker",
                            context.getString(R.string.notification_new)
                        )
                    )
                    .setContentTitle(getString(data, "title", ""))
                    .setContentText(getString(data, "subtitle", ""))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setAutoCancel(true)
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(getString(data, "message", ""))
                            .setBigContentTitle(getString(data, "title", ""))
                    )
                    .setSound(getDefaultSoundUri())
                    .setContentIntent(openIntent)
                    .addAction(R.drawable.ic_clear, "CLOSE", closeIntent)
                    .addAction(R.drawable.ic_touch_app, "SHOW ME", openIntent)

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_TYPE_TEXT_ID, notificationBuilder.build())

            tracker.trackServerNotificationShown()
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private val showTextAndImageNotification =
        fun(
            data: Map<String, String>,
            image: Bitmap,
        ) {
            try {

                val intentOpen = getAppOpenPendingIntent(NOTIFICATION_TYPE_TEXT_AND_IMAGE)
                val intentClose =
                    getAppClosePendingIntent(NOTIFICATION_TYPE_TEXT_AND_IMAGE)

                val notificationBuilder =
                    NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_wimm_notification)
                        .setLargeIcon(
                            (ContextCompat
                                .getDrawable(context, R.drawable.wimm_logo) as BitmapDrawable)
                                .bitmap
                        )
                        .setTicker(getString(data, "ticker", ""))
                        .setContentTitle(getString(data, "title", ""))
                        .setContentText(getString(data, "subtitle", ""))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                        .setAutoCancel(true)
                        .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setSound(getDefaultSoundUri())
                        .setContentIntent(intentOpen)
                        .addAction(R.drawable.ic_clear, "CLOSE", intentClose)
                        .addAction(R.drawable.ic_touch_app, "SHOW ME", intentOpen)

                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                    .notify(NOTIFICATION_TYPE_IMAGE_ID, notificationBuilder.build())

                tracker.trackServerNotificationShown()
            } catch (e: Exception) {
                Logger.record(e)
            }
        }

    private fun showImageNotification(data: Map<String, String>, image: Bitmap) {
        try {

            val intentOpen = getAppOpenPendingIntent(NOTIFICATION_TYPE_IMAGE)
            val intentClose = getAppClosePendingIntent(NOTIFICATION_TYPE_IMAGE)

            val notificationBuilder =
                NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_wimm_notification)
                    .setTicker(getString(data, "ticker", ""))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
                    .setSound(getDefaultSoundUri())
                    .setContentIntent(intentOpen)
                    .addAction(R.drawable.ic_clear, "CLOSE", intentClose)
                    .addAction(R.drawable.ic_touch_app, "SHOW ME", intentOpen)

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_TYPE_TEXT_AND_IMAGE_ID, notificationBuilder.build())

            tracker.trackServerNotificationShown()
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private fun showQuoteContentNotification(
        data: Map<String, String>,
        image: Bitmap,
    ) {
        try {
            val content = ContentUtils.convertToContent(data) ?: return

            val bitmap: Bitmap? = QuoteUtils.getShareView(
                context, content.subtitle, content.extra, image
            )?.let {
                BitmapUtils.scaleBitmapForNotification(BitmapUtils.getBitmapFromView(it))
            }

            val notificationBuilder =
                NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_wimm_notification)
                    .setTicker(getString(data, "title", ""))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap ?: image))
                    .setSound(getDefaultSoundUri())
                    .setContentIntent(getContentViewPendingIntent(content))
                    .addAction(
                        R.drawable.ic_share, "SHARE",
                        getContentShareOthersPendingIntent(content)
                    )
                    .addAction(
                        R.drawable.ic_whatsapp, "WHATSAPP",
                        getContentShareWhatsAppPendingIntent(content)
                    )

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_TYPE_CONTENT_ID, notificationBuilder.build())

            tracker.trackServerNotificationShown()
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private fun showContentNotification(
        data: Map<String, String>,
        image: Bitmap
    ) {
        try {
            val content = ContentUtils.convertToContent(data) ?: return
            val notificationBuilder =
                NotificationCompat.Builder(context, Constants.NOTIFICATION_DEFAULT_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_wimm_notification)
                    .setTicker(getString(data, "title", ""))
                    .setContentTitle(getString(data, "title", ""))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
                    .setSound(getDefaultSoundUri())
                    .setContentIntent(getContentViewPendingIntent(content))
                    .addAction(
                        R.drawable.ic_share, "SHARE",
                        getContentShareOthersPendingIntent(content)
                    )
                    .addAction(
                        R.drawable.ic_whatsapp, "WHATSAPP",
                        getContentShareWhatsAppPendingIntent(content)
                    )

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_TYPE_CONTENT_ID, notificationBuilder.build())

            tracker.trackServerNotificationShown()
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    fun showMoodNotification() {
        try {
            val moodIntent = getMoodPendingIntent()
            val journalIntent = getJournalPendingIntent()

            val notificationBuilder =
                NotificationCompat.Builder(context, Constants.NOTIFICATION_HAPPINESS_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_wimm_notification)
                    .setTicker(context.getString(R.string.how_your_feeling))
                    .setContentTitle(context.getString(R.string.notification_mood_title))
                    .setContentText(context.getString(R.string.notification_mood_text))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setAutoCancel(true)
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .setBigContentTitle(context.getString(R.string.notification_mood_title))
                            .bigText(context.getString(R.string.notification_mood_text))
                    )
                    .setSound(getDefaultSoundUri())
                    .setContentIntent(moodIntent)
                    .addAction(R.drawable.ic_very_happy, "HAPPINESS", moodIntent)
                    .addAction(R.drawable.ic_calendar_today, "JOURNAL", journalIntent)

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_TYPE_MOOD_ID, notificationBuilder.build())

//            context.startActivity(TransparentMoodActivity.getStartIntent(context)) TODO
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    @SuppressLint("CheckResult")
    private fun downloadAndShowImageNotification(
        data: Map<String, String>,
        handler: (data: Map<String, String>, image: Bitmap) -> Unit
    ) {

        val thumbnail = getString(data, "thumbnail", null) ?: return

        scope.launch {
            try {
                val loader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(thumbnail)
                    .allowHardware(true)
                    .build()

                val result = (loader.execute(request) as SuccessResult).drawable
                val bitmap = (result as BitmapDrawable).bitmap
                handler(data, bitmap)
            } catch (e: Throwable) {
                Logger.record(e)
            }

        }
    }

    private fun getAppOpenPendingIntent(notificationType: String): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_1,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, notificationType)
                putExtra(NOTIFICATION_ACTION, ACTION_APP_LAUNCH)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )


    private fun getAppClosePendingIntent(
        notificationType: String
    ): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_2,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, notificationType)
                putExtra(NOTIFICATION_ACTION, ACTION_CLOSE)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getContentViewPendingIntent(content: Content): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_3,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, NOTIFICATION_TYPE_CONTENT)
                putExtra(NOTIFICATION_DATA_CONTENT, content)
                putExtra(NOTIFICATION_ACTION, ACTION_CONTENT_VIEW)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getContentShareWhatsAppPendingIntent(
        content: Content
    ): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_4,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, NOTIFICATION_TYPE_CONTENT)
                putExtra(NOTIFICATION_DATA_CONTENT, content)
                putExtra(NOTIFICATION_ACTION, ACTION_CONTENT_SHARE_WHATSAPP)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getContentShareOthersPendingIntent(
        content: Content
    ): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_5,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, NOTIFICATION_TYPE_CONTENT)
                putExtra(NOTIFICATION_DATA_CONTENT, content)
                putExtra(NOTIFICATION_ACTION, ACTION_CONTENT_SHARE_OTHERS)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getMoodPendingIntent(): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_6,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, NOTIFICATION_TYPE_MOOD)
                putExtra(NOTIFICATION_ACTION, ACTION_MOOD_RECORD)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getJournalPendingIntent(): PendingIntent =
        PendingIntent.getService(
            context,
            ACTION_ID_7,
            Intent(context, NotificationCallbackService::class.java).apply {
                putExtra(NOTIFICATION_TYPE, NOTIFICATION_TYPE_MOOD)
                putExtra(NOTIFICATION_ACTION, ACTION_JOURNAL_RECORD)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun getString(data: Map<String, String>, tag: String, default: String?): String? =
        data[tag] ?: default

    private fun getDefaultSoundUri() =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

}