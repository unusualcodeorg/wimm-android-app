package com.whereismymotivation.fcm

import android.app.IntentService
import android.app.NotificationManager
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.remote.utils.NetworkHelper
import com.whereismymotivation.data.repository.RemoteConfigRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.MainActivity
import com.whereismymotivation.utils.log.Logger
import javax.inject.Inject

// TODO: Remove this service
class NotificationCallbackService : IntentService("NotificationCallbackService") {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var tracker: Tracker

    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var remoteConfigRepository: RemoteConfigRepository

    private val ACTION_SCREEN_NAVIGATION = "ACTION_SCREEN_NAVIGATION"
    private val KEY_SCREEN_TO_GO = "KEY_SCREEN_TO_GO"

    val SCREEN_MOOD = "SCREEN_MOOD"
    val SCREEN_JOURNAL = "SCREEN_JOURNAL"

    fun getMainStartIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

    fun getMainStartIntent(context: Context, screenToGo: String): Intent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = ACTION_SCREEN_NAVIGATION
            putExtra(KEY_SCREEN_TO_GO, screenToGo)
        }

    override fun onHandleIntent(intent: Intent?) {
        intent?.run {
            try {
                val notificationManager = applicationContext
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                getStringExtra(NotificationBuilder.NOTIFICATION_ACTION)?.let { action ->
                    when (action) {
                        NotificationBuilder.ACTION_APP_LAUNCH -> {
                            launchApp()
                        }

                        NotificationBuilder.ACTION_CLOSE -> {
                        }

                        NotificationBuilder.ACTION_CONTENT_VIEW -> {
                            getParcelableExtra<Content>(NotificationBuilder.NOTIFICATION_DATA_CONTENT)?.run {
                                userRepository.getCurrentUser()?.let {
                                    TaskStackBuilder.create(applicationContext)
                                        .addNextIntent(
                                            getMainStartIntent(applicationContext)
                                        )
                                        .addNextIntent(
                                            getContentStartIntent(
                                                applicationContext,
                                                this
                                            )
                                        )
                                        .startActivities()
                                }
                            }
                            tracker.trackServerNotificationClick()
                        }

                        NotificationBuilder.ACTION_CONTENT_SHARE_WHATSAPP -> {
                            getParcelableExtra<Content>(NotificationBuilder.NOTIFICATION_DATA_CONTENT)?.run {
                                closeNotificationDrawer()
//                                applicationContext.startActivity(
//                                    TransparentShareActivity
//                                        .getStartIntent(applicationContext, this, true)
//                                )
                                tracker.trackServerNotificationShareWhatsApp()
                            }
                        }

                        NotificationBuilder.ACTION_CONTENT_SHARE_OTHERS -> {
                            getParcelableExtra<Content>(NotificationBuilder.NOTIFICATION_DATA_CONTENT)?.run {
                                closeNotificationDrawer()
//                                applicationContext.startActivity(
//                                    TransparentShareActivity
//                                        .getStartIntent(applicationContext, this, false)
//                                )
                                tracker.trackServerNotificationShareOthers()
                            }
                        }

                        NotificationBuilder.ACTION_MOOD_RECORD -> {
                            closeNotificationDrawer()
                            userRepository.getCurrentUser()?.let {
                                applicationContext.startActivity(
                                    getMainStartIntent(
                                        applicationContext,
                                        SCREEN_MOOD
                                    )
                                )
                            }
                            tracker.trackMoodNotificationMoodRecord()
                        }

                        NotificationBuilder.ACTION_JOURNAL_RECORD -> {
                            closeNotificationDrawer()
                            userRepository.getCurrentUser()?.let {
                                applicationContext.startActivity(
                                    getMainStartIntent(
                                        applicationContext,
                                        SCREEN_JOURNAL
                                    )
                                )
                            }
                            tracker.trackMoodNotificationJournalRecord()
                        }

                        else -> {
                        }
                    }
                }

                getStringExtra(NotificationBuilder.NOTIFICATION_TYPE)?.let { type ->
                    when (type) {
                        NotificationBuilder.NOTIFICATION_TYPE_UNKNOWN -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_UNKNOWN_ID)
                        }

                        NotificationBuilder.NOTIFICATION_TYPE_TEXT -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_TEXT_ID)
                        }

                        NotificationBuilder.NOTIFICATION_TYPE_IMAGE -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_IMAGE_ID)
                        }

                        NotificationBuilder.NOTIFICATION_TYPE_TEXT_AND_IMAGE -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_TEXT_AND_IMAGE_ID)
                        }

                        NotificationBuilder.NOTIFICATION_TYPE_CONTENT -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_CONTENT_ID)
                        }

                        NotificationBuilder.NOTIFICATION_TYPE_MOOD -> {
                            notificationManager.cancel(NotificationBuilder.NOTIFICATION_TYPE_MOOD_ID)
                        }
                    }
                }
            } catch (e: Exception) {
                Logger.record(e)
            }
        }
    }

    private fun launchApp() {
        applicationContext.startActivity(getMainStartIntent(applicationContext))
    }

    private fun closeNotificationDrawer() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
            }
        } catch (e: Exception) {
            Logger.record(e)
        }
    }

    private fun getContentStartIntent(context: Context, content: Content): Intent =
        when (content.category) {
            Content.Category.AUDIO, Content.Category.VIDEO, Content.Category.ARTICLE -> TODO()
//                WebViewActivity.getStartIntent(context, content)
            Content.Category.IMAGE -> TODO()
//                ImageActivity.getStartIntent(context, content)
            Content.Category.YOUTUBE -> TODO()
//                YoutubeActivity.getStartIntent(context, content)
            Content.Category.FACEBOOK_VIDEO -> TODO()
//                FacebookVideoActivity.getStartIntent(context, content)
            Content.Category.QUOTE -> TODO()
//                QuoteActivity.getStartIntent(context, content)
            Content.Category.MENTOR_INFO -> TODO()
//                MentorDetailActivity.getStartIntent(context, content.id)
            Content.Category.TOPIC_INFO -> TODO()
//                TopicDetailActivity.getStartIntent(context, content.id)
        }

}