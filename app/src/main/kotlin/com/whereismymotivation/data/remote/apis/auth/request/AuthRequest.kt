package com.whereismymotivation.data.remote.apis.auth.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicAuthRequest(
    @Json(name = "email")
    val email: String,

    @Json(name = "password")
    val password: String
)

@JsonClass(generateAdapter = true)
data class GoogleAuthRequest(
    @Json(name = "googleUserId")
    val googleUserId: String,

    @Json(name = "googleIdToken")
    val idToken: String,
)

@JsonClass(generateAdapter = true)
data class FacebookAuthRequest(
    @Json(name = "fbUserId")
    val facebookUserId: String,

    @Json(name = "fbAccessToken")
    val facebookAccessToken: String,
)