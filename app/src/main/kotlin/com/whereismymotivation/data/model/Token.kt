package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Token(

    @Json(name = "accessToken")
    val accessToken: String,

    @Json(name = "refreshToken")
    val refreshToken: String

) : Parcelable