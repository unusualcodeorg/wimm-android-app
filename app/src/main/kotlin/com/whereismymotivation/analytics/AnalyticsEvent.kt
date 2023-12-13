package com.whereismymotivation.analytics

data class AnalyticsEvent(val name: String) {

    object Name {

        const val APP_OPEN = "app_open"
        const val APP_CLOSE = "app_close"

        const val SIGN_UP = "sign_up"
        const val LOGIN = "login"
        const val SHARE_CONTENT = "share_content"
        const val LIKE_CONTENT = "like_content"
        const val BOOKMARK_CONTENT = "bookmark_content"

        const val SPLASH_SCREEN_STATE = "splash_screen_state"
        const val HOME_SCREEN_STATE = "home_screen_state"
        const val MENTORS_SCREEN_STATE = "mentors_screen_state"
        const val TOPICS_SCREEN_STATE = "topics_screen_state"
        const val MENTOR_DETAILS_SCREEN_STATE = "mentor_details_screen_state"
        const val TOPIC_DETAILS_SCREEN_STATE = "topic_details_screen_state"
        const val SEARCH_SCREEN_STATE = "search_screen_state"
        const val WELCOME_SCREEN_STATE = "welcome_screen_state"
        const val YOUTUBE_SCREEN_STATE = "youtube_screen_state"
        const val YOUTUBE_WEB_SCREEN_STATE = "youtube_web_screen_state"
        const val FACEBOOK_VIDEO_SCREEN_STATE = "facebook_video_screen_state"
        const val QUOTE_SCREEN_STATE = "quote_screen_state"
        const val IMAGE_SCREEN_STATE = "image_screen_state"

        const val MENTORS_SEARCH_MORE_CLICK = "mentors_search_more_click"

        const val SEARCH_MORE_TOPICS_CLICK = "search_more_topics_click"
        const val SEARCH_BAR_CLICK = "search_bar_click"
        const val WELCOME_DONE_CLICK = "welcome_done_click"
        const val MENTOR_FOLLOW_CHANGE_CLICK = "mentor_follow_change_click"
        const val TOPIC_FOLLOW_CHANGE_CLICK = "topic_follow_change_click"

        const val MENTOR_CONTENTS_TOGGLE = "topic_contents_toggle"
        const val TOPIC_CONTENTS_TOGGLE = "topic_contents_toggle"

        const val SELECTED_APP_SECTION = "selected_app_section"

        const val DIALOG_FEEDBACK_CHOICE = "dialog_feedback_choice"

        const val DIALOG_RATE_US_SHOWN = "dialog_rate_us_shown"
        const val DIALOG_RATE_US_CHOICE = "dialog_rate_us_choice"
        const val DIALOG_RATE_US_STARS = "dialog_rate_us_stars"

        const val SERVER_NOTIFICATION_RECEIVED = "server_notification_received"
        const val SERVER_NOTIFICATION_SHOWN = "server_notification_shown"
        const val SERVER_NOTIFICATION_CLICK = "server_notification_click"
        const val SERVER_NOTIFICATION_SHARE_OTHERS = "server_notification_share_others"
        const val SERVER_NOTIFICATION_SHARE_WHATSAPP = "server_notification_share_whatsapp"

        const val MOOD_NOTIFICATION_RECEIVED = "mood_notification_received"
        const val MOOD_NOTIFICATION_SHOWN = "mood_notification_shown"
        const val MOOD_NOTIFICATION_CLICK = "mood_notification_click"
        const val MOOD_NOTIFICATION_MOOD_RECORD = "mood_notification_mood_record"
        const val MOOD_NOTIFICATION_JOURNAL_RECORD = "mood_notification_journal_record"

        const val HOME_FEED_CONTENT_CLICK = "home_feed_content_click"
        const val SHEET_CONTENT_CLICK = "sheet_content_click"
        const val SEARCH_RESULT_CLICK = "search_result_click"

        const val MENTOR_CARD_CLICK = "mentor_card_click"
        const val TOPIC_CARD_CLICK = "search_card_click"

        const val SETTINGS_UPDATE_APP = "settings_update_app"
        const val SETTINGS_FEEDBACK = "settings_feedback"
        const val SETTINGS_RATE_US = "settings_rate_us"
        const val SETTINGS_INVITE_YOUR_FRIEND = "settings_invite_your_friend"
        const val SETTINGS_LOGOUT = "settings_logout"
        const val SETTINGS_LOGIN = "settings_login"
        const val HOME_SCREEN_SETTINGS = "home_screen_settings"

        const val PROFILE_SCREEN_LOGIN = "profile_screen_login"

        const val MOOD_SELECTED = "mood_selected"
        const val JOURNAL_ADDED = "journal_added"
        const val JOURNAL_REMOVED = "journal_removed"

        const val MOOD_NOTIFICATION_SCREEN_SHOW = "mood_notification_screen_show"
        const val MOOD_NOTIFICATION_SCREEN_DISMISS = "mood_notification_screen_dismiss"
        const val MOOD_NOTIFICATION_BUTTON_DISMISS_CLICK = "mood_notification_btn_dismiss_click"
        const val MOOD_NOTIFICATION_EMPTY_SPACE_CLICK = "mood_notification_empty_space_click"

        const val MOOD_AND_JOURNAL_SYNC_SUCCESS = "mood_and_journal_sync_success"
        const val MOOD_AND_JOURNAL_SYNC_FAILURE = "mood_and_journal_sync_failure"
    }
}