package com.whereismymotivation.data.remote

import com.whereismymotivation.di.AccessTokenInfo
import com.whereismymotivation.di.ApiKeyInfo
import com.whereismymotivation.di.AppVersionCodeInfo
import com.whereismymotivation.di.DeviceIdInfo
import com.whereismymotivation.di.UserInfo
import com.whereismymotivation.utils.common.ResultFetcher
import javax.inject.Inject

class RequestHeaders @Inject constructor(
    @ApiKeyInfo val apiKey: String,
    @AccessTokenInfo val accessTokenFetcher: ResultFetcher<String>,
    @DeviceIdInfo val deviceIdFetcher: ResultFetcher<String>,
    @AppVersionCodeInfo val appVersionCodeFetcher: ResultFetcher<Long>,
) {
    object Key {
        const val API_AUTH_TYPE = "API_AUTH_TYPE"
        const val AUTH_PUBLIC = "$API_AUTH_TYPE: public"
        const val AUTH_PROTECTED = "$API_AUTH_TYPE: protected"
    }

    enum class Type constructor(val value: String) {
        PUBLIC("public"),
        PROTECTED("protected")
    }

    enum class Param(val value: String) {
        API_KEY("x-api-key"),
        DEVICE_ID("x-device-id"),
        ANDROID_VERSION("x-android-version"),
        TIMEZONE_OFFSET("x-timezone-offset"),
        ACCESS_TOKEN("Authorization")
    }
}