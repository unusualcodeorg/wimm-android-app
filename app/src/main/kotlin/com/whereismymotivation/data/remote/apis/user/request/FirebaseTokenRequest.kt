package com.whereismymotivation.data.remote.apis.user.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FirebaseTokenRequest(

    @Json(name = "firebaseToken")
    val type: String
)