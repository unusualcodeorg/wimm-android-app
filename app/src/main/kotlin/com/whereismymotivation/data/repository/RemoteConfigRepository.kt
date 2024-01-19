package com.whereismymotivation.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.whereismymotivation.utils.config.RemoteKey.HOME_PAGE_CONTENT_COUNT
import com.whereismymotivation.utils.config.RemoteKey.INVITE_FRIEND_MESSAGE
import com.whereismymotivation.utils.config.RemoteKey.SHARE_WATERMARK_TEXT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigRepository @Inject constructor(private val config: FirebaseRemoteConfig) {

    fun getHomePageContentCount() = config.getLong(HOME_PAGE_CONTENT_COUNT).toInt()
    fun getPageContentCount() = config.getLong(HOME_PAGE_CONTENT_COUNT).toInt()
    fun getInviteFriendMessage(): String = config.getString(INVITE_FRIEND_MESSAGE)
    fun getShareWatermarkText(): String = config.getString(SHARE_WATERMARK_TEXT)

}