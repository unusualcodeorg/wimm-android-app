package com.whereismymotivation.data.remote.apis.content.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentSubmissionRequest(

    @Json(name = "id")
    val contentId: String
)