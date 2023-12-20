package com.whereismymotivation.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.whereismymotivation.utils.config.RemoteKey.HOME_FEED_LAST_SEEN_REFRESH_DAYS
import com.whereismymotivation.utils.config.RemoteKey.HOME_PAGE_CONTENT_COUNT
import com.whereismymotivation.utils.config.RemoteKey.INVITE_FRIEND_MESSAGE
import com.whereismymotivation.utils.config.RemoteKey.RATING_DIALOG_LATER_TO_SHOW
import com.whereismymotivation.utils.config.RemoteKey.RATING_DIALOG_MAX_NUM_OF_TIMES_SHOW_RATING_IN_CURRENT_APP_VERSION
import com.whereismymotivation.utils.config.RemoteKey.RATING_DIALOG_MAX_RATING
import com.whereismymotivation.utils.config.RemoteKey.RATING_DIALOG_MIN_GAP_APP_OPEN_SHOW_RATING_COUNT
import com.whereismymotivation.utils.config.RemoteKey.SHARE_WATERMARK_TEXT
import com.whereismymotivation.utils.config.RemoteKey.YOUTUBE_API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepository @Inject constructor(private val config: FirebaseRemoteConfig) {

    fun getHomePageContentCount() = config.getLong(HOME_PAGE_CONTENT_COUNT).toInt()
    fun getPageContentCount() = config.getLong(HOME_PAGE_CONTENT_COUNT).toInt()
    fun getInviteFriendMessage(): String = config.getString(INVITE_FRIEND_MESSAGE)
    fun getShareWatermarkText(): String = config.getString(SHARE_WATERMARK_TEXT)
    fun getYouTubeApiKey(): String = config.getString(YOUTUBE_API_KEY)

    fun getRatingDialogMaxRating(): Int =
        config.getLong(RATING_DIALOG_MAX_RATING).toInt()

    fun getRatingDialogMinGapAppOpenCount(): Int =
        config.getLong(RATING_DIALOG_MIN_GAP_APP_OPEN_SHOW_RATING_COUNT).toInt()

    fun getRatingDialogMaxShowCountInCurrentVersion(): Int =
        config.getLong(RATING_DIALOG_MAX_NUM_OF_TIMES_SHOW_RATING_IN_CURRENT_APP_VERSION).toInt()

    fun getHomeFeedLastSeenRefreshDays(): Long = config.getLong(HOME_FEED_LAST_SEEN_REFRESH_DAYS)

    fun getRatingDialogLaterToShow(): Boolean = config.getBoolean(RATING_DIALOG_LATER_TO_SHOW)

}