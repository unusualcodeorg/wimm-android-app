package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Topic(

    @Json(name = "_id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "coverImgUrl")
    val coverImgUrl: String,

    @Json(name = "subscribed")
    val subscribed: Boolean?

): Parcelable