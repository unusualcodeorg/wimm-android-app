package com.whereismymotivation.analytics

import com.google.firebase.analytics.FirebaseAnalytics

data class AnalyticsEventParam<out T>(val name: String, val value: T) {


    // Note: all values should be from FirebaseAnalytics.Param class
    // for the param to come up in reports
    object Name {

        const val LOGIN_MODE = FirebaseAnalytics.Param.ITEM_NAME
        const val STATE = FirebaseAnalytics.Param.ITEM_NAME
        const val SECTION = FirebaseAnalytics.Param.ITEM_NAME
        const val TYPE = FirebaseAnalytics.Param.ITEM_NAME
        const val CHOICE = FirebaseAnalytics.Param.ITEM_NAME
        const val CATEGORY = FirebaseAnalytics.Param.ITEM_CATEGORY
        const val ITEM = FirebaseAnalytics.Param.ITEM_ID
        const val MEDIUM = FirebaseAnalytics.Param.MEDIUM

        // must be associate with either long or double value
        const val VALUE = FirebaseAnalytics.Param.VALUE

        // must be associated with long value
        const val QUANTITY = FirebaseAnalytics.Param.QUANTITY

        const val CONTENT = FirebaseAnalytics.Param.CONTENT
    }

    object Value {

        const val OPEN = "open"
        const val CLOSE = "close"

        const val GOOGLE = "google"
        const val FACEBOOK = "facebook"

        const val LATER = "later"
        const val SUBMIT = "submit"
        const val CANCEL = "cancel"
        const val PLAY_STORE = "google_play_store"

        const val NOTIFICATION = "notification"
        const val FLOATING = "floating"
        const val SYNC = "sync"
        const val UPDATE_APP = "update_app"
        const val FEEDBACK = "feedback"
        const val RATE_US = "rate_us"
        const val INVITE = "invite"
        const val LOGOUT = "logout"
        const val LOGIN = "login"

        const val HOME = "home"
        const val MENTORS = "mentors"
        const val SEARCH = "search"
        const val MOTIVATION_BOX = "motivation_box"

        const val PROFILE = "profile"
        const val SEARCH_MENTOR = "search_mentor"
        const val SEARCH_TOPIC = "search_topic"
        const val SUBSCRIBE_MENTOR = "subscribe_mentor"
        const val REMOVE_MENTOR = "remove_mentor"
        const val SUBSCRIBE_TOPIC = "subscribe_topic"
        const val REMOVE_TOPIC = "remove_topic"

        const val FULLSCREEN = "fullscreen"
        const val SHARE = "share"
        const val ID = "id"
        const val CONTENT_TYPE = "content_type"

        const val CONTENT_SHOW = "content_show"
        const val CONTENT_HIDE = "content_hide"
        const val CONTENT = "content"

    }
}