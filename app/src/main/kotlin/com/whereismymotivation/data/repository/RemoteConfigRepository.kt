package com.whereismymotivation.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepository @Inject constructor(private val config: FirebaseRemoteConfig) {

    companion object {
        private const val HOME_PAGE_CONTENT_COUNT = "home_page_content_count"
        private const val INVITE_FRIEND_MESSAGE = "invite_friend_message"
        private const val SHARE_WATERMARK_TEXT = "share_water_mark_text"
        private const val YOUTUBE_API_KEY = "youtube_api_key"
        private const val HOME_FEED_LAST_SEEN_REFRESH_DAYS = "home_feed_last_seen_refresh_days"
        private const val RATING_DIALOG_LATER_TO_SHOW = "rating_dialog_later_to_show"

        private const val RATING_DIALOG_MAX_RATING =
            "rating_dialog_max_rating"
        private const val RATING_DIALOG_MIN_GAP_APP_OPEN_SHOW_RATING_COUNT =
            "rating_dialog_min_gap_app_open_count"
        private const val RATING_DIALOG_MAX_NUM_OF_TIMES_SHOW_RATING_IN_CURRENT_APP_VERSION =
            "rating_dialog_max_show_count_in_current_version"
    }

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