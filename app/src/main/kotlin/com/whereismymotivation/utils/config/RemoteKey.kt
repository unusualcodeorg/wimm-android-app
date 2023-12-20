package com.whereismymotivation.utils.config

object RemoteKey {

    const val INVITE_FRIEND_MESSAGE = "invite_friend_message"
    const val HOME_PAGE_CONTENT_COUNT = "home_page_content_count"
    const val HOME_FEED_LAST_SEEN_REFRESH_DAYS = "home_feed_last_seen_refresh_days"
    const val SHARE_WATER_MARK_TEXT = "share_water_mark_text"
    const val RATING_DIALOG_MAX_RATING = "rating_dialog_max_rating"
    const val RATING_DIALOG_MIN_GAP_APP_OPEN_COUNT = "rating_dialog_min_gap_app_open_count"
    const val RATING_DIALOG_MAX_SHOW_COUNT = "rating_dialog_max_show_count_in_current_version"
    const val RATING_DIALOG_LATER_TO_SHOW = "rating_dialog_later_to_show"
    const val YOUTUBE_API_KEY = "youtube_api_key"
    const val SHARE_WATERMARK_TEXT = "share_water_mark_text"
    const val RATING_DIALOG_MIN_GAP_APP_OPEN_SHOW_RATING_COUNT =
        "rating_dialog_min_gap_app_open_count"
    const val RATING_DIALOG_MAX_NUM_OF_TIMES_SHOW_RATING_IN_CURRENT_APP_VERSION =
        "rating_dialog_max_show_count_in_current_version"

    val defaults = hashMapOf(
        INVITE_FRIEND_MESSAGE to "I liked this app and invite you to give it a try.",
        HOME_PAGE_CONTENT_COUNT to 10,
        HOME_FEED_LAST_SEEN_REFRESH_DAYS to 5,
        SHARE_WATER_MARK_TEXT to "whereismymotivation.com",
        RATING_DIALOG_MAX_RATING to 5,
        RATING_DIALOG_MIN_GAP_APP_OPEN_COUNT to 5,
        RATING_DIALOG_MAX_SHOW_COUNT to 2,
        RATING_DIALOG_LATER_TO_SHOW to false,
        YOUTUBE_API_KEY to "changeit",
        SHARE_WATERMARK_TEXT to "WhereIsMyMotivation",
        RATING_DIALOG_MIN_GAP_APP_OPEN_SHOW_RATING_COUNT to 5,
        RATING_DIALOG_MAX_NUM_OF_TIMES_SHOW_RATING_IN_CURRENT_APP_VERSION to 5,
    )
}