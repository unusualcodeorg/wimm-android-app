package com.whereismymotivation.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.whereismymotivation.data.local.db.entity.Journal

@JsonClass(generateAdapter = true)
data class JournalsRequest(

    @Json(name = "journals")
    val journals: List<Journal>
)