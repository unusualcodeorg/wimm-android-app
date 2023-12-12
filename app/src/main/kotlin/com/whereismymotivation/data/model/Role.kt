package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Role(

    @Json(name = "_id")
    val id: String,

    @Json(name = "code")
    val code: String

) : Parcelable