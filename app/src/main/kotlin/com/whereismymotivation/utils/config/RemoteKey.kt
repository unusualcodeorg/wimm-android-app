package com.whereismymotivation.utils.config

object RemoteKey {

    const val INVITE_FRIEND_MESSAGE = "invite_friend_message"
    const val HOME_PAGE_CONTENT_COUNT = "home_page_content_count"
    const val HOME_FEED_LAST_SEEN_REFRESH_DAYS = "home_feed_last_seen_refresh_days"
    const val SHARE_WATER_MARK_TEXT = "share_water_mark_text"
    const val SHARE_WATERMARK_TEXT = "share_water_mark_text"

    val defaults = hashMapOf(
        INVITE_FRIEND_MESSAGE to "I liked this app and invite you to give it a try.",
        HOME_PAGE_CONTENT_COUNT to 10,
        HOME_FEED_LAST_SEEN_REFRESH_DAYS to 5,
        SHARE_WATER_MARK_TEXT to "whereismymotivation.com",
        SHARE_WATERMARK_TEXT to "WhereIsMyMotivation",
    )
}