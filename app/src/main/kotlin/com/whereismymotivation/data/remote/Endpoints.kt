package com.whereismymotivation.data.remote

object Endpoints {

    const val LOGIN_DEVICE = "login/device-id"
    const val LOGIN_BASIC = "login/basic"
    const val LOGIN_GOOGLE = "login/google"
    const val LOGIN_FACEBOOK = "login/facebook"
    const val REFRESH_TOKEN = "token/refresh"
    const val LOGOUT = "logout"

    const val SUBSCRIPTION_SUBSCRIBE = "subscription/subscribe"
    const val SUBSCRIPTION_UNSUBSCRIBE = "subscription/unsubscribe"
    const val SUBSCRIPTION_MENTOR_LIST = "subscription/mentors"
    const val SUBSCRIPTION_TOPIC_LIST = "subscription/topics"
    const val SUBSCRIPTION_INFO_MENTOR = "subscription/info/mentor/{id}"
    const val SUBSCRIPTION_INFO_TOPIC = "subscription/info/topic/{id}"
    const val RECOMMENDED_MENTOR_LIST = "subscription/recommendation/mentors"
    const val RECOMMENDED_TOPIC_LIST = "subscription/recommendation/topics"

    const val SIMILAR_CONTENT_LIST = "content/similar"
    const val CONTENT_LIST = "contents/rotated"
    const val MENTOR_CONTENT_LIST = "content/mentor/{id}/list"
    const val TOPIC_CONTENT_LIST = "content/topic/{id}/list"
    const val META_CONTENT = "meta/content"
    const val MY_BOX_CONTENT_LIST = "content/my/box/list"
    const val CREATE_PRIVATE_CONTENT = "content/private"
    const val DELETE_PRIVATE_CONTENT = "content/private/{id}"
    const val BOOKMARK_CONTENT = "content/bookmark"
    const val REMOVE_CONTENT_BOOKMARK = "content/bookmark/{contentId}"
    const val SUBMIT_PRIVATE_CONTENT = "content/private/submit"
    const val UNSUBMIT_PRIVATE_CONTENT = "content/private/unsubmit"
    const val PUBLISH_GENERAL_CONTENT = "content/modify/publish/general"

    const val CONTENT_DETAIL = "content/id/{id}"
    const val CONTENT_MARK_VIEW = "content/mark/view"
    const val CONTENT_MARK_LIKE = "content/mark/like"
    const val CONTENT_MARK_UNLIKE = "content/mark/unlike"
    const val CONTENT_MARK_SHARE = "content/mark/share"

    const val MENTOR_DETAIL = "mentor/id/{id}"
    const val TOPIC_DETAIL = "topic/id/{id}"

    const val SEARCH = "search"
    const val MESSAGE = "contact"
    const val PROFILE = "profile"

    const val STORAGE_MOOD = "storage/moods"
    const val STORAGE_JOURNAL = "storage/journals"
}