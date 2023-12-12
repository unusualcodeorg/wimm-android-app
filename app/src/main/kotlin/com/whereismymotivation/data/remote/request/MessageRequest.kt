package com.whereismymotivation.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageRequest(
    @Json(name = "type")
    val type: String,

    @Json(name = "message")
    val message: String
)