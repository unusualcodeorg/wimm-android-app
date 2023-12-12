package com.whereismymotivation.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.whereismymotivation.data.model.Content

@JsonClass(generateAdapter = true)
data class ApiDataResponse<T>(

    @Json(name = "statusCode")
    val statusCode: String,

    @Json(name = "message")
    val message: String,

    @Json(name = "data")
    val data: T
)