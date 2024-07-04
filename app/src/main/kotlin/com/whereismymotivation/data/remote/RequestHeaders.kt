package com.whereismymotivation.data.remote

import androidx.annotation.Keep
import com.whereismymotivation.di.qualifier.AccessTokenInfo
import com.whereismymotivation.di.qualifier.ApiKeyInfo
import com.whereismymotivation.di.qualifier.AppVersionCodeInfo
import com.whereismymotivation.di.qualifier.DeviceIdInfo
import com.whereismymotivation.utils.common.ResultFetcherBlocking
import javax.inject.Inject

class RequestHeaders @Inject constructor(
    @ApiKeyInfo val apiKey: String,
    @AccessTokenInfo val accessTokenFetcher: ResultFetcherBlocking<String>,
    @DeviceIdInfo val deviceIdFetcher: ResultFetcherBlocking<String>,
    @AppVersionCodeInfo val appVersionCodeFetcher: ResultFetcherBlocking<Long>,
) {
    object Key {
        const val API_AUTH_TYPE = "API_AUTH_TYPE"
        const val AUTH_PUBLIC = "$API_AUTH_TYPE: public"
        const val AUTH_PROTECTED = "$API_AUTH_TYPE: protected"
    }

    @Keep
    enum class Type(val value: String) {
        PUBLIC("public"),
        PROTECTED("protected")
    }

    @Keep
    enum class Param(val value: String) {
        API_KEY("x-api-key"),
        DEVICE_ID("x-device-id"),
        ANDROID_VERSION("x-android-version"),
        TIMEZONE_OFFSET("x-timezone-offset"),
        ACCESS_TOKEN("Authorization")
    }
}