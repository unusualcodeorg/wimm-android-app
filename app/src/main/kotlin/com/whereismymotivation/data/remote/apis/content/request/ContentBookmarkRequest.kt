package com.whereismymotivation.data.remote.apis.content.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContentBookmarkRequest(

    @Json(name = "contentId")
    val contentId: String
)