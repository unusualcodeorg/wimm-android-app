package com.whereismymotivation.utils.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkError(
    val status: Int = -1,

    @Json(name = "statusCode")
    val statusCode: String = "-1",

    @Json(name = "message")
    val message: String = "Something went wrong"
)