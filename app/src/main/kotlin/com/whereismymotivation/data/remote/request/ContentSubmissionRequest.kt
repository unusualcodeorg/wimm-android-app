package com.whereismymotivation.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentSubmissionRequest(

    @Json(name = "contentId")
    val contentId: String
)