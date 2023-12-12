package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UniversalSearchResult(

    @Json(name = "id")
    val id: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "category")
    val category: Content.Category,

    @Json(name = "thumbnail")
    val thumbnail: String?,

    @Json(name = "extra")
    val extra: String
) : Parcelable