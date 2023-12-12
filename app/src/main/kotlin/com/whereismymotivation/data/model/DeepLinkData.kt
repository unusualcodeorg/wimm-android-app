package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DeepLinkData(
    @Json(name = "_id")
    val id: String,

    @Json(name = "_id")
    val type: String,

    @Json(name = "primary")
    val primary: String?,

    @Json(name = "secondary")
    val secondary: String?,

    @Json(name = "tertiary")
    val tertiary: String?,

    @Json(name = "extra")
    val extra: String?

) : Parcelable