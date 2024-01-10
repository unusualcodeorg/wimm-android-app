package com.whereismymotivation.data.remote.apis.user.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.whereismymotivation.data.local.db.entity.Mood

@JsonClass(generateAdapter = true)
data class MoodsRequest(
    @Json(name = "moods")
    val moods: List<Mood>
)