package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MetaContent(

    @Json(name = "category")
    val category: Content.Category,

    @Json(name = "title")
    val title: String?,

    @Json(name = "subtitle")
    val subtitle: String?,

    @Json(name = "thumbnail")
    val thumbnail: String?,

    @Json(name = "extra")
    val extra: String

) : Parcelable